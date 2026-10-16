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
cp "$root"/tools/release/consumer/*.java "$scratch/"
cat > "$scratch/BUILD.bazel" <<'BUILD'
load("@rules_java//java:defs.bzl", "java_test")
load("@j2cl//build_defs:rules.bzl", "j2cl_library")
java_test(
    name = "consumer",
    srcs = ["Consumer.java", "Catalog.java", "EmptyRouter.java", "Browser.java"],
    main_class = "isolated.Consumer",
    use_testrunner = False,
    javacopts = ["--release", "17", "-Werror", "-XDaddTypeAnnotationsToSymbol=true", "-Xep:NullAway:ERROR", "-XepOpt:NullAway:OnlyNullMarked=true", "-XepOpt:NullAway:JSpecifyMode=true", "-XepOpt:NullAway:AssertsEnabled=true", "-XepOpt:NullAway:CastToNonNullMethod=java.util.Objects.requireNonNull"],
    plugins = ["@router_fu//:router_fu_processor_plugin", "@router_fu//third_party/java:nullaway_plugin"],
    deps = ["@router_fu//:core", "@arez//:core"],
    jvm_flags = ["-ea", "-Dbraincheck.environment=development"],
)
j2cl_library(
    name = "browser",
    srcs = ["Browser.java"],
    experimental_enable_jspecify_support_do_not_enable_without_jspecify_static_checking_or_you_might_cause_an_outage = True,
    deps = ["@router_fu//:core-j2cl", "@router_fu//third_party/java:jspecify-j2cl"],
)
BUILD
cd "$scratch"
bazel --output_base="$scratch/bazel" test //:consumer --test_output=errors
bazel --output_base="$scratch/bazel" build //:browser
printf 'External Bazel consumer passed using assembled runtime, processor and J2CL sources\n'
