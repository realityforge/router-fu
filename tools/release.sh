#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/.." && pwd)"
cd "$root"
if [[ "${1:-}" != validate || $# -gt 2 ]]; then
  echo 'Usage: tools/release.sh validate [version]' >&2
  exit 2
fi
version="${2:-0.0.0-SNAPSHOT}"
bazel build //tools/release:maven_artifacts --//tools/release:version="$version"
bazel test //tools/release/src/test/java/isolated:consumer --test_output=errors
python3 tools/release/validate.py "$(bazel info bazel-bin)/tools/release" "$version"
