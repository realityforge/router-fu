# Build patches

These patches accompany the exact tool and source revisions in `MODULE.bazel`. Consumer roots must apply the
same overrides because Bazel does not inherit overrides from dependencies.

* J2CL: Bazel 9 rule forwarding, configurable class outputs and tests, Java 21 execution, deterministic test
  generation, and compiler/Maven fixes needed by the pinned revision.
* Closure rules: Bazel 9 loads, protobuf rule ownership, bounded compiler memory, runtime test defines,
  and loopback access for WebDriver while retaining filesystem sandboxing.
* Web testing: declared runfiles, pure Go runner binaries, and removal of the runner's environment dump.
* Java rules: deterministic archives without workspace build metadata.
* C++ rules: allow the selected macOS SDK root in sandboxed native tool compilation.
* JsInterop base: suppress warnings on the two upstream class-conversion exports that J2CL cannot expose.
* Arez: share Braincheck's source targets and update its JVM imports to base 1.2.0 and JSpecify 1.0.1.

Remove a patch when an adopted upstream revision contains the fix; keep product compatibility logic out of these files.
