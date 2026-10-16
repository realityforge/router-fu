# Contributing

Submit changes through a pull request. Keep each change focused, describe the problem it solves, and
update the changelog when behavior changes. Participation follows the [Code of Conduct](CODE_OF_CONDUCT.md).

## Build and test

Install Bazelisk, then run:

```sh
tools/check.sh
```

Bazel downloads the pinned Java toolchains, Chromium, and WebDriver. Building the browser compiler also
requires a C++ toolchain: Xcode command-line tools on macOS or GCC on Linux.

The gate checks formatting, hermeticity, processor tests, JavaScript tests, processor coverage,
external J2CL consumption, and reproducible publication archives. Runtime unit tests use JUnit compiled
by J2CL and run in Chromium. Browser integration tests exercise native regular expressions, hash
navigation, and the generated Arez example in development and optimized production configurations.

For a smaller check:

```sh
bazel test //:processor_tests --test_output=errors
bazel test //:browser_tests --test_output=errors
bazel test -c opt //:browser_tests --test_output=errors
```

Processor JVM coverage must reach 95% of lines and 85% of branches. Reports are written under
`reports/coverage/` and retained by CI. Runtime behavior is tested as JavaScript.

## Source layout and checks

Use package-aligned `src/main/java` and `src/test/java` paths, including for build tools. Tool packages
use `router.fu.tools.<tool>`. Put BUILD files beside their sources and list Java files explicitly.
Compiler fixtures belong under `processor/src/test/resources/fixtures`.

The J2CL wrapper runs Error Prone and NullAway during J2CL's compilation. Its internal `*_java` targets
expose those existing outputs for packaging and formatting without another compile. The processor and
build tools compile for the JVM.

The shared checker policy lives in `third_party/java/rules.bzl` and also applies to fixture compilations.
Generated bodies are checked. Assertions establish internal invariants, and redundant
`Objects.requireNonNull` calls fail the checks. Keep runtime checks at unchecked JavaScript boundaries.

Format changes with:

```sh
tools/java_format.sh write
bazel run //tools/buildifier:buildifier
```

## Processor goldens

Processor test classes compile once. Separate Bazel targets own route, parameter, callback, and router
goldens, plus diagnostics, checker controls, and formatter-failure tests. Each target declares its fixture
inputs. Update the affected golden group through the processor:

```sh
tools/update_goldens.sh route_goldens
```

The other golden groups are `parameter_goldens`, `callback_goldens`, and `router_goldens`. The updater
regenerates only that group's files and runs its tests. Inspect the resulting diff; do not edit generated
goldens by hand.

## Using the example

```sh
bazel build //example/src/main/webapp:example
```

Serve `bazel-bin/example/src/main/webapp/example/` with a static HTTP server, then open its index page.

## External Bazel consumers

Applications use the J2CL target `//:core` and the JVM annotation-processing plugin
`//:router_fu_processor_plugin`. Runtime JVM targets are not an application API.

Copy the dependency overrides and patches from `MODULE.bazel` into the consuming root; Bazel does not
inherit dependency-module overrides. `tools/check_bazel_consumer.sh` exercises a separate workspace
against the public targets and the runtime source archive. The [release guide](tools/release/README.md)
lists the publication artifacts.
