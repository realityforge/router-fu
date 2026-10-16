#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"
bazel build //processor:tests
scratch="$(mktemp -d "${TMPDIR:-/tmp}/router-fu-goldens.XXXXXX")"
trap 'rm -rf "$scratch"' EXIT
cp -R processor/src/test/fixtures "$scratch/fixtures"
# Bazel run unlocks the server before Java exits; keep fixture compilation outside Bazel's shared test temp tree.
mkdir "$scratch/tmp"
bazel run //processor:tests -- "--wrapper_script_flag=--jvm_flag=-Djava.io.tmpdir=$scratch/tmp" "--wrapper_script_flag=--jvm_flag=-Drouter.fu.fixture_dir=$scratch/fixtures" "--wrapper_script_flag=--jvm_flag=-Drouter.fu.output_fixture_data=true" -d "$scratch/test-output"
for tree in expected expectedFormatted; do
  cp -R "$scratch/fixtures/$tree/." "processor/src/test/fixtures/$tree/"
done
bazel test //processor:tests --test_output=errors
