# Publication artifacts

Build the runtime and annotation processor artifacts with:

```sh
bazel build //tools/release:maven_artifacts
```

The files are written to `bazel-bin/tools/release/`. Each module, `router-fu-core` and
`router-fu-processor`, produces a jar, a `-sources.jar`, a `-javadoc.jar`, and a POM under the Maven group
`org.realityforge.router.fu`. Individual build targets are listed in [BUILD.bazel](BUILD.bazel).

The processor jar includes its service registration and embeds Proton and JavaPoet under relocated
packages. The runtime declares Braincheck, JsInterop, and JSpecify as dependencies in its POM.
Bazel consumers can use `@router_fu//tools/release:core_j2cl_sources` for the runtime source archive.

## Versions

Both the Router-fu artifact version and the Braincheck dependency version default to `0.0.0-SNAPSHOT`.
For a release, set `router_fu_version` to the release version and `braincheck_version` to a published
Braincheck version compatible with the source revision pinned in [MODULE.bazel](../../MODULE.bazel),
then build:

```sh
bazel build //tools/release:maven_artifacts \
  --//tools/release:version="$router_fu_version" \
  --//tools/release:braincheck_version="$braincheck_version"
```

These settings select the versions in the generated POMs. Output filenames stay the same.
