#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
case "${1:-write}" in
  check) exec bazel build //:java_format_check ;;
  write) exec bazel run @rules_palantir_java_format//:java_format -- --root=core/src --root=processor/src/main --root=processor/src/test/java --root=example/src/main/java --root=tools/release/src/main/java --root=tools/release/src/test/java --root=tools/verification/src/main/java --root=browser ;;
  *) echo 'Usage: tools/java_format.sh [write|check]' >&2; exit 2 ;;
esac
