# Build patches

The retained JsInterop Base patch suppresses J2CL warnings on two upstream class-conversion exports.
[BUILD.bazel](BUILD.bazel) lists the patch, and [MODULE.bazel](../../../MODULE.bazel) owns its application.

Consumer roots must copy the override because Bazel does not inherit dependency overrides. Remove it when
an adopted upstream revision makes the suppression unnecessary.
