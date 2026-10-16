#!/usr/bin/env bash
set -euo pipefail
root="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$root"
bazel build //tools/release:maven_artifacts
first="$(bazel info bazel-bin)/tools/release"
scratch="$(mktemp -d "${TMPDIR:-/tmp}/router-fu-repro.XXXXXX")"
cleanup() {
  bazel --output_base="$scratch/bazel" shutdown >/dev/null 2>&1 || true
  find "$scratch" -type d -exec chmod u+w {} +
  rm -rf "$scratch"
}
trap cleanup EXIT
bazel --output_base="$scratch/bazel" build //tools/release:maven_artifacts
second="$(bazel --output_base="$scratch/bazel" info bazel-bin)/tools/release"
for module in core processor; do
  for suffix in .jar -sources.jar -javadoc.jar .pom; do
    cmp "$first/router-fu-$module$suffix" "$second/router-fu-$module$suffix"
  done
done
echo 'All eight publication artifacts match an independent build'
