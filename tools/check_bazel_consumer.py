#!/usr/bin/env python3
"""Exercise the public Bazel API from an isolated application."""

import json
import shutil
import subprocess
import tempfile
import xml.etree.ElementTree as ET
from pathlib import Path


ROOT = Path(__file__).resolve().parent.parent


def run(*args, cwd=ROOT, **kwargs):
    return subprocess.run(args, cwd=cwd, check=True, text=True, **kwargs)


def check_surface():
    result = run(
        "bazel", "query", "visible(@rules_java//java:defs.bzl, //...:*)",
        "--output=xml", capture_output=True,
    )
    actual = {
        node.attrib.get("generating-rule", node.attrib["name"])
        for node in ET.fromstring(result.stdout)
    }
    expected = {"//:core", "//:processor"}
    assert actual == expected, f"Public targets: {sorted(actual)}; expected {sorted(expected)}"
    print("Verified exactly two public Bazel targets", flush=True)


def check_consumer(workspace):
    module = (ROOT / "MODULE.bazel").read_text().replace(
        'module(name = "router_fu")', 'module(name = "router_fu_consumer")', 1,
    )
    (workspace / "MODULE.bazel").write_text(
        module + '\nbazel_dep(name = "router_fu")\n'
        f'local_path_override(module_name = "router_fu", path = {json.dumps(str(ROOT))})\n'
    )
    (workspace / "BUILD.bazel").write_text('package(default_visibility = ["//visibility:private"])\n')
    for name in (".bazelversion", "maven_install.json"):
        shutil.copy2(ROOT / name, workspace / name)
    (workspace / ".bazelrc").write_text((ROOT / ".bazelrc").read_text().split("try-import", 1)[0])
    shutil.copytree(ROOT / "third_party/bazel/patches", workspace / "third_party/bazel/patches")
    sources = workspace / "src"
    sources.mkdir()
    shutil.copy2(ROOT / "tools/browser/chromium.json", sources / "chromium.json")
    for package in (
        "tools/release/src/test/java/router/fu/tools/release/consumer",
        "browser/src/test/java/router/fu/browser",
        "example/src/main/java/router/fu/example",
    ):
        for source in (ROOT / package).glob("*.java"):
            shutil.copy2(source, sources / source.name)
    shutil.copy2(ROOT / "example/src/main/java/router/fu/example/entry.js", sources / "entry.js")
    (sources / "BUILD.bazel").write_text('''\
load("@j2cl//build_defs:rules.bzl", "j2cl_library", "j2cl_test")
load("@rules_closure//closure:defs.bzl", "closure_js_binary", "closure_js_library", "closure_js_test", "custom_browser")

config_setting(name = "production", values = {"compilation_mode": "opt"})
LEVEL = select({":production": "ADVANCED", "//conditions:default": "BUNDLE"})
DEFINES = select({
    ":production": {"braincheck.environment": "production", "braincheck.check_api_invariants": "false", "braincheck.check_invariants": "false", "braincheck.verbose_error_messages": "false", "consumer.environment": "production"},
    "//conditions:default": {"braincheck.environment": "development", "braincheck.check_api_invariants": "true", "braincheck.check_invariants": "true", "braincheck.verbose_error_messages": "true", "consumer.environment": "development"},
})
COMPILER_DEFINES = select({
    ":production": ["--define=braincheck.environment='production'", "--define=braincheck.check_api_invariants=false", "--define=braincheck.check_invariants=false", "--define=braincheck.verbose_error_messages=false", "--define=consumer.environment='production'"],
    "//conditions:default": ["--define=braincheck.environment='development'", "--define=consumer.environment='development'"],
})

custom_browser(name = "chromium", browser = "@rules_browsers//browsers/chromium:chromium", metadata = "chromium.json")
j2cl_library(
    name = "consumer", testonly = True,
    srcs = ["Catalog.java", "ConsumerTest.java", "EmptyRouter.java", "BrowserProbe.java", "BrowserRouter.java"],
    plugins = ["@router_fu//:processor"],
    experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
    deps = ["@router_fu//:core", "@arez//third_party:braincheck_core-j2cl", "@arez//third_party:jspecify-j2cl", "@j2cl//:jsinterop-annotations-j2cl", "@j2cl//:junit"],
)
j2cl_test(
    name = "routing", test_class = "router.fu.tools.release.consumer.ConsumerTest",
    runtime_deps = [":consumer"], browsers = [":chromium"], compilation_level = LEVEL,
)
closure_js_test(
    name = "configuration", srcs = ["configuration_test.js"],
    deps = [":consumer", "@rules_closure//closure/testing/library:asserts", "@rules_closure//closure/testing/library:testsuite"],
    entry_points = ["consumer.ConfigurationTest"], browsers = [":chromium"],
    compilation_level = LEVEL, defs = COMPILER_DEFINES, runtime_defines = DEFINES,
)
j2cl_library(
    name = "example", srcs = ["AppState.java", "AuthState.java", "Example.java", "MyRouter.java", "package-info.java"],
    plugins = ["@router_fu//:processor", "@arez//:processor"],
    experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
    deps = ["@router_fu//:core", "@arez//:core-j2cl", "@arez//third_party:jspecify-j2cl", "@j2cl//:jsinterop-annotations-j2cl"],
)
closure_js_library(name = "entry", srcs = ["entry.js"], deps = [":example"])
closure_js_binary(name = "application", deps = [":entry"], entry_points = ["router.fu.example.Main"], compilation_level = LEVEL)
''')
    (sources / "configuration_test.js").write_text('''\
goog.module('consumer.ConfigurationTest');
const Probe = goog.require('router.fu.browser.BrowserProbe');
const asserts = goog.require('goog.testing.asserts');
const testSuite = goog.require('goog.testing.testSuite');
/** @define {string} */
const environment = goog.define('consumer.environment', 'development');
testSuite({
  testSharedConfiguration() {
    asserts.assertEquals(environment === 'development', Probe.checksEnabled());
    Probe.start();
    Probe.stop();
  },
});
''')
    for mode in ("fastbuild", "opt"):
        run("bazel", "build", "-c", mode, "//src:application", cwd=workspace)
        run("bazel", "test", "-c", mode, "//src:routing", "//src:configuration",
            "--test_output=errors", "--local_test_jobs=1", cwd=workspace)

    denied = workspace / "denied"
    denied.mkdir()

    def expect_denied(content, diagnostic):
        (denied / "BUILD.bazel").write_text(content)
        result = subprocess.run(
            ["bazel", "build", "--nobuild", "//denied:target"], cwd=workspace,
            text=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT,
        )
        assert result.returncode and diagnostic in result.stdout, result.stdout

    for label in (
        "core:core-j2cl", "core:libcore-j2cl.jar", "core/src/main/java/router/fu:core",
        "core/src/main/java/router/fu:Backend.java",
        "core/src/main/java/router/fu/annotations:annotations",
        "processor/src/main/java/router/fu/processor:processor", "processor:processor",
        "processor:_processor_java",
        "tools/release:core_sources", "tools/release:processor_jar",
        "third_party/java:braincheck_core-j2cl",
    ):
        expect_denied(f'filegroup(name = "target", srcs = ["@router_fu//{label}"])\n', "not visible")
    for label, symbol in (
        ("tools/browser:java.bzl", "java_j2cl_library"),
        ("tools/browser:rules.bzl", "browser_test"),
        ("third_party/java:rules.bzl", "java_library"),
        ("tools/release:release.bzl", "release_jar"),
        ("processor/src/test/java/router/fu/processor:test_rules.bzl", "processor_test"),
    ):
        expect_denied(
            f'load("@router_fu//{label}", "{symbol}")\nfilegroup(name = "target")\n',
            "not visible for loading",
        )
    print("External routing, example, configuration and visibility checks passed", flush=True)


def main():
    check_surface()
    with tempfile.TemporaryDirectory(prefix="router-fu-consumer-") as directory:
        workspace = Path(directory)
        try:
            check_consumer(workspace)
        finally:
            subprocess.run(["bazel", "shutdown"], cwd=workspace, check=False)


if __name__ == "__main__":
    main()
