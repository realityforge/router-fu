# Publication

Build the runtime and processor jars, sources, Javadocs, and POMs with:

```sh
bazel build //tools/release:maven_artifacts
```

The runtime source archive is available as `//tools/release:core_sources` and
`//tools/release:core_j2cl_sources`. Use it for J2CL consumption. Individual artifact targets are listed in
[BUILD.bazel](BUILD.bazel). The processor jar, `//tools/release:processor_jar`, embeds relocated Proton and
JavaPoet dependencies and its service registration.
Shared browser runtimes remain dependencies.

`tools/release.sh validate 0.0.0-SNAPSHOT` checks archive contents, metadata, and a J2CL consumer of the
assembled sources and processor. `tools/release/check_reproducibility.sh` compares all eight artifacts
with an independent build. Both are part of `tools/check.sh`.

## Version selection

The default artifact version and Braincheck POM dependency are `0.0.0-SNAPSHOT`. Before a Maven release,
choose a published Braincheck version matching the pinned source API with
`--//tools/release:braincheck_version=VERSION`, then select Router-fu's version with
`--//tools/release:version=VERSION`. Validation rejects a non-snapshot release with a snapshot Braincheck
dependency. The validation command does not upload or tag a release.

Dependency revisions and build overrides are maintained in [MODULE.bazel](../../MODULE.bazel).
Patch purposes are documented in [the patch directory](../../third_party/bazel/patches/README.md).
