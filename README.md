# Router-Fu: A framework agnostic, state producing router

[![Verify](https://github.com/realityforge/router-fu/actions/workflows/bazel.yml/badge.svg)](https://github.com/realityforge/router-fu/actions/workflows/bazel.yml)

## What is Router-Fu?

Router-Fu is a modern router for your web application. Traditional routers match on the url and render components,
templates or pages but a Router-Fu router matches on the url and outputs state. The application is able to treat
route state like any other application state.

## The Design

The core component of the system is the `Route`. A `Route` is a named pattern against which urls can be matched
against. The pattern also identifies parameters that are extracted from the url and potentially constraints that
each pattern must be validated against. The `Route` also has the ability to construct a url given a set of
parameters.

A typical route may be represented by `"/:function/:org_context/event/:eventId"` and would extract the `function`,
`org_context` and `eventId` parameters from any url that matches the above.

If a url is matched by a `Route` and the parameters satisfy the constraints then a `RouteMatchCallback` is invoked
on the `Route` that can perform custom behaviour and returns an enum that indicates whether the match was:

* no-match. i.e. Further validation of the parameters identified the route as non-matching.
* terminal. i.e. The match was successful and no more routes need to be matched.
* non-terminal. i.e. The match was successful but continue to attempt to match more routes.

The `Router` is an ordered list of routes. During routing a path is passed to the `Router` that walks through
each `Route` in order, until a terminal route is identified or there are no more routes. For each route matched
a `RouteState` is created that contains the `Route` matched, the parameters extracted from the route and whether
the route was "terminal". The routing method on the `Router` returns a `Location` which contains an ordered
list of `RouteState` instances.

The `Router` also exposes the routes so that application code can generate paths for the route given a set of
parameters. It also provides methods via which the application can request that the location be changed.

The `Location` object is effectively immutable and can easily be passed to other frameworks that have their
own state management tools. In an [`Arez`](https://github.com/realityforge/arez), it would expected that you wrap
the `Location` to make it observable.

## Build and test

Install Bazelisk and run `tools/check.sh`. The repository pins Bazel 9.3.0 and downloads its Java toolchains.
The gate checks formatting, action hermeticity, JVM tests, generated Java bodies, Chromium tests in development
and optimized production modes, JVM coverage, external consumption, and reproducible publication archives.
Chromium, WebDriver and the Java toolchains are downloaded by Bazel. The native browser compiler tools also
require a C++ toolchain (Xcode command-line tools on macOS, GCC on Linux).

JVM coverage must reach 75% of lines and 65% of branches in core, and 95% of lines and 85% of branches
in the processor. The core report includes native browser paths, which are exercised separately in Chromium.

Useful commands:

```sh
bazel test //:jvm_tests --test_output=errors
bazel test //:browser_tests --test_output=errors
bazel test -c opt //:browser_tests --test_output=errors
bazel build //example/src/main/webapp:example
# Serve bazel-bin/example/src/main/webapp/example/ with a static HTTP server to open the example.
tools/java_format.sh write
tools/update_goldens.sh
```

The example uses J2CL and the same generated Arez router as the browser tests. GWT modules, its compiler,
and its development server have been retired.

Java sources, including build tools, use package-aligned `src/main/java` or `src/test/java` directories.
Java packages own their BUILD files and compile package-local sources. `java_j2cl_library` accepts only
local source files. The root targets assemble publication artifacts and group tests. Processor fixtures
live under `processor/src/test/resources/fixtures` as test data compiled by the processor tests.

## Nullability

Router-fu uses JSpecify 1.0.1. Runtime and processor packages default to `@NullMarked`; generated service
interfaces and router implementations declare that default themselves, including in unmarked consumer packages.
Route-state and bound-parameter getters return `@Nullable` values until their route matches. Initial location
storage is nullable while activation is in progress; `getLocation()` retains its asserted non-null contract.

Native regular expressions can have unmatched capture groups. `RouteMatchCallback` and
`RouteState.getParameters()` therefore expose `Map<Parameter, @Nullable String>`. Callbacks receiving the map
must use that value type. `RouteState.getParameterValue()` requires a captured value and retains its assertion;
all parameters emitted by the router generator capture a string, including empty optional parameters.
`Route.buildLocation()` requires non-null parameter values.

NullAway runs in JSpecify mode at ERROR, checks generated bodies, and assumes assertions are enabled for
internal invariants. `Objects.requireNonNull` is configured as a cast so redundant calls fail the gate.
Unchecked JavaScript casts and nullable DOM lookups keep their runtime checks.

## Bazel consumers

Use `//:core`, `//:core-j2cl`, `//:processor` and `//:router_fu_processor_plugin` as the public Java,
J2CL and annotation-processing targets. These use the assembled publication artifacts. The processor jar
contains relocated Proton and JavaPoet dependencies and its service registration.

The publication files are `//tools/release:core_jar`, `//tools/release:core_sources`,
`//tools/release:core_j2cl_sources`, `//tools/release:processor_jar` and
`//tools/release:processor_sources`. The J2CL source target is an alias of the runtime source archive;
this library needs no alternate sources or native JavaScript companions.

Bazel consumers must copy the root dependency overrides and patches from `MODULE.bazel`: dependency-module
overrides do not propagate to the consuming root. `tools/check_bazel_consumer.sh` exercises this from a separate
workspace. Source-only consumption still runs Router-fu's JVM compilation and nullability checks.
The pinned Arez patch shares the Braincheck source runtime and updates its JVM imports to JsInterop base 1.2.0
and JSpecify 1.0.1. It avoids linking a second Braincheck implementation when both libraries are used together.

See [the publication boundary](tools/release/README.md) for archive validation and release prerequisites.

# Credit

* [Stock Software](http://www.stocksoftware.com.au/) for providing significant support in building and maintaining
  the library, particularly at it's inception.

* This toolkit drew inspiration from [troch/path-parser](https://github.com/troch/path-parser) and
  [router5](http://router5.github.io/).
