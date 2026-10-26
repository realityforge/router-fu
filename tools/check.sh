#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"
test "$(bazel --version)" = 'bazel 9.3.1'
bazel run //tools/buildifier:buildifier_check
tools/java_format.sh check
bazel run //tools/ahab:hermeticity.check
bazel run //tools/ahab:hermeticity.check -- --compilation-mode=opt
bazel build //:core //:processor //example/src/main/webapp:example //tools/release:maven_artifacts
bazel test //:all_tests --test_output=errors
bazel test -c opt //:browser_tests --test_output=errors
bazel coverage -c dbg //:processor_tests --combined_report=lcov '--instrumentation_filter=^//processor/src/main/java/' --test_output=errors
coverage_report="$(bazel info -c dbg output_path)/_coverage/_coverage_report.dat"
mkdir -p reports/coverage
cp -f "$coverage_report" reports/coverage/jvm.lcov
bazel run //tools/verification/src/main/java/router/fu/tools/verification:coverage_check -- "$root/reports/coverage/jvm.lcov" "$root/reports/coverage/summary.txt"
python3 tools/check_bazel_consumer.py
git diff --check
echo 'All Router-fu checks passed.'
