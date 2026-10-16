#!/usr/bin/env bash
set -euo pipefail
if [[ $# -ne 1 ]]; then
  echo 'Usage: tools/update_goldens.sh <route_goldens|parameter_goldens|callback_goldens|router_goldens>' >&2
  exit 1
fi
case "$1" in
  route_goldens) features=(route) ;;
  parameter_goldens) features=(parameter) ;;
  callback_goldens) features=(callback) ;;
  router_goldens) features=(router router_ref arez nested) ;;
  *) echo "No generated goldens are owned by processor test target: $1" >&2; exit 1 ;;
esac
root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"
target="//processor/src/test/java/router/fu/processor:$1"
fixtures=processor/src/test/resources/fixtures
bazel build "$target"
scratch="$(mktemp -d "${TMPDIR:-/tmp}/router-fu-goldens.XXXXXX")"
trap 'rm -rf "$scratch"' EXIT
for tree in input expected expectedFormatted; do
  mkdir -p "$scratch/fixtures/$tree/com/example"
  for feature in "${features[@]}"; do
    cp -R "$fixtures/$tree/com/example/$feature" "$scratch/fixtures/$tree/com/example/"
  done
done
# Keep fixture compilation outside Bazel's shared test temp tree.
mkdir "$scratch/tmp"
bazel run "$target" -- "--wrapper_script_flag=--jvm_flag=-Djava.io.tmpdir=$scratch/tmp" "--wrapper_script_flag=--jvm_flag=-Drouter.fu.fixture_dir=$scratch/fixtures" "--wrapper_script_flag=--jvm_flag=-Drouter.fu.output_fixture_data=true" -d "$scratch/test-output"
for tree in expected expectedFormatted; do
  for feature in "${features[@]}"; do
    cp -R "$scratch/fixtures/$tree/com/example/$feature/." "$fixtures/$tree/com/example/$feature/"
  done
done
bazel test "$target" --test_output=errors
