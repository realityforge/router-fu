#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"
test "$(bazel --version)" = 'bazel 9.3.0'
bazel run //tools/buildifier:buildifier_check
tools/java_format.sh check
bazel run //tools/ahab:hermeticity.check
bazel run //tools/ahab:hermeticity.check -- --compilation-mode=opt
bazel build //:core //:processor //:core-j2cl //example:example //tools/release:maven_artifacts
bazel test //:all_tests --test_output=errors
bazel test -c opt //:browser_tests --test_output=errors
bazel coverage -c dbg //:jvm_tests --combined_report=lcov '--instrumentation_filter=^//(core|processor)(:|/),-/src/test/' --test_output=errors
coverage_report="$(bazel info -c dbg output_path)/_coverage/_coverage_report.dat"
mkdir -p reports/coverage
cp -f "$coverage_report" reports/coverage/jvm.lcov
bazel run //tools/verification:coverage_check -- "$root/reports/coverage/jvm.lcov" "$root/reports/coverage/summary.txt"
tools/check_bazel_consumer.sh
tools/release.sh validate 0.0.0-SNAPSHOT
tools/release/check_reproducibility.sh
bazel build //tools/release:maven_artifacts
git diff --check
echo 'All Router-fu checks passed.'
