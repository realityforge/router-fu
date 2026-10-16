# Publication boundary

`bazel build //tools/release:maven_artifacts` assembles the core and processor jars, source archives,
Javadocs and POMs. The public root imports consume these same jars. Archive entries are normalized;
`tools/release/check_reproducibility.sh` compares all eight files with an independent Bazel build.

`tools/release.sh validate 0.0.0-SNAPSHOT` checks archive contents, service registration, dependency metadata,
and a downstream JVM consumer using the shaded processor. `tools/check_bazel_consumer.sh` also checks a separate
Bazel root against the public imports and J2CL source archive. Neither command uploads, tags or releases anything.

The processor embeds and relocates Proton and JavaPoet. The runtime exports Braincheck, JsInterop base,
JsInterop annotations and JSpecify through its Bazel import; it does not embed a second copy of those runtimes.
The J2CL target uses the same Braincheck compilation as the pinned, patched Arez module.

The default artifact version and Braincheck POM dependency are `0.0.0-SNAPSHOT`. Before any Maven release,
select an actual published Braincheck version containing the pinned migration using
`--//tools/release:braincheck_version=VERSION`, then select the Router-fu version using
`--//tools/release:version=VERSION`. A non-snapshot validation rejects a snapshot Braincheck dependency.
No unreleased version number is inferred from the source pin. Publishing is a separate maintainer operation.

The immutable source dependencies are:

| Module | Revision |
| --- | --- |
| Braincheck | `e34ad2685d8f691c7bac82bfc829fe64a4266bfb` |
| Proton | `d156a9d2eee74f1577cf8b7a03cd9a5a67a637ee` |
| Sting (checker tooling) | `b313be65dc351090e31fb53862f345c260f01e68` |
| Arez (example and fixtures) | `491a15a93363431eb1df4ba81e3e8088a4153ce3` |

Arez's source pin is the head of merged [PR #157](https://github.com/arez/arez/pull/157).
`third_party/bazel/patches/arez_shared_runtimes.patch` changes only its dependency boundary:
it adds the Braincheck module, aliases both Braincheck compilations to that module, and updates the two
Maven imports and their source archives. The upstream source pin stays immutable.
