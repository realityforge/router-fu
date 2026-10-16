#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
scratch="$(mktemp -d "${TMPDIR:-/tmp}/router-fu-consumer.XXXXXX")"
cleanup() {
  (cd "$scratch" && bazel --output_base="$scratch/bazel" shutdown >/dev/null 2>&1) || true
  find "$scratch" -type d -exec chmod u+w {} +
  rm -rf "$scratch"
}
trap cleanup EXIT
cp "$root/.bazelrc" "$root/.bazelversion" "$root/maven_install.json" "$scratch/"
printf 'exports_files(["maven_install.json"])\n' > "$scratch/BUILD.bazel"
mkdir -p "$scratch/third_party/bazel"
cp -R "$root/third_party/bazel/patches" "$scratch/third_party/bazel/"
python3 - "$root" "$scratch" <<'PY'
from pathlib import Path
import json,sys
root,dest=map(Path,sys.argv[1:])
module=(root/'MODULE.bazel').read_text().replace('module(name = "router_fu")','module(name = "router_fu_consumer")')
module+='\nbazel_dep(name = "router_fu")\nlocal_path_override(module_name = "router_fu", path = '+json.dumps(str(root))+')\n'
(dest/'MODULE.bazel').write_text(module)
PY
consumer_path=src/test/java/router/fu/tools/release/consumer
mkdir -p "$scratch/$consumer_path"
cp "$root"/tools/release/"$consumer_path"/*.java "$scratch/$consumer_path/"
cat > "$scratch/$consumer_path/BUILD.bazel" <<'BUILD'
load("@j2cl//build_defs:rules.bzl", "j2cl_import", "j2cl_library", "j2cl_test")
load("@router_fu//third_party/java:rules.bzl", "J2CL_JAVACOPTS")
load("@rules_webtesting//web:web.bzl", "custom_browser")

j2cl_import(
    name = "jspecify",
    jar = "@jspecify//file:jspecify.jar",
)

custom_browser(
    name = "chromium",
    browser = "@rules_webtesting//browsers:chromium-local",
    environment = {"CHROME_LOG_FILE": "/dev/stderr"},
)

j2cl_library(
    name = "test_classes",
    testonly = True,
    srcs = ["Catalog.java", "ConsumerTest.java", "EmptyRouter.java"],
    experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
    javacopts = J2CL_JAVACOPTS,
    plugins = ["@router_fu//:router_fu_processor_plugin", "@sting//third_party/java:nullaway_plugin"],
    deps = ["@router_fu//:core", ":jspecify", "@j2cl//:junit"],
)

j2cl_test(
    name = "consumer",
    test_class = "router.fu.tools.release.consumer.ConsumerTest",
    runtime_deps = [":test_classes"],
    browsers = [":chromium"],
    compilation_level = "BUNDLE",
    runtime_defines = {"braincheck.environment": "development"},
)

j2cl_library(
    name = "archive",
    srcs = ["@router_fu//tools/release:core_j2cl_sources"],
    experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
    javacopts = J2CL_JAVACOPTS,
    plugins = ["@sting//third_party/java:nullaway_plugin"],
    deps = [
        "@arez//third_party/java:braincheck_core-j2cl",
        "@j2cl//:jsinterop-annotations-j2cl",
        "@jsinterop_base//:jsinterop-base-j2cl",
        ":jspecify",
    ],
)
BUILD
cd "$scratch"
bazel --output_base="$scratch/bazel" test "//$consumer_path:consumer" --test_output=errors
bazel --output_base="$scratch/bazel" build "//$consumer_path:archive"
printf 'External J2CL consumer passed using public targets and assembled sources\n'
