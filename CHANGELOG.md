# Change Log

### Unreleased

* Update the `org.realityforge.javax.annotation` artifact to version `1.1.1`.
* Update the `org.realityforge.proton` artifacts to version `0.65`.
* Update the `org.realityforge.guiceyloops` artifact to version `0.113`.
* Update the `org.realityforge.arez` artifacts to version `0.214`.

### [v0.42](https://github.com/realityforge/router-fu/tree/v0.42) (2023-01-25) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.41...v0.42)

Changes in this release:

* Update the `org.realityforge.arez` artifacts to version `0.207`.
* Add `router.fu.verbose_out_of_round.errors` to the list of processor options declared as supported. This was not required in Java 8 but is required in Java 17 to have these options accessed by processor without errors.

### [v0.41](https://github.com/realityforge/router-fu/tree/v0.41) (2023-01-23) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.40...v0.41)

Changes in this release:

* Update the `org.realityforge.arez` artifacts to version `0.206`.

### [v0.40](https://github.com/realityforge/router-fu/tree/v0.40) (2022-05-10) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.39...v0.40)

Changes in this release:

* Update the `org.realityforge.proton` artifacts to version `0.59`.
* Migrate to Java9 `@javax.annotation.processing.Generated` annotation rather than non-standard `@javax.annotation.Generated`.

### [v0.39](https://github.com/realityforge/router-fu/tree/v0.39) (2022-05-02) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.38...v0.39)

Changes in this release:

* Update the `org.realityforge.proton` artifacts to version `0.58`.
* Update the `org.realityforge.grim` artifacts to version `0.09`.
* Update the `org.realityforge.akasha` artifacts to version `0.30`.
* Update the `org.realityforge.arez` artifacts to version `0.205`.
* Upgrade to use JDK 17 as a baseline.

### [v0.38](https://github.com/realityforge/router-fu/tree/v0.38) (2021-12-28) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.37...v0.38)

Changes in this release:

* Upgrade the `org.realityforge.proton` artifacts to version `0.54`. To improve debuggability and profiling of the annotation processor. This has added the `router.fu.debug` and `router.fu.profile` annotation processor options.

### [v0.37](https://github.com/realityforge/router-fu/tree/v0.37) (2021-12-09) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.36...v0.37)

Changes in this release:

* Update the `org.realityforge.guiceyloops` artifact to version `0.110`.
* Update the `org.realityforge.grim` artifacts to version `0.06`.
* Add support for `Route.optionalParameters` parameter that define names of parameters that can be empty strings.

### [v0.36](https://github.com/realityforge/router-fu/tree/v0.36) (2021-11-10) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.35...v0.36)

Changes in this release:

* Update the `org.realityforge.akasha` artifacts to version `0.29`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.52`. This fixes a crash that occurs with concurrent, incremental builds within the IntelliJ IDE. (This is the crash reported with message `javax.annotation.processing.FilerException: Attempt to recreate a file for type ...`).

### [v0.35](https://github.com/realityforge/router-fu/tree/v0.35) (2021-11-08) · [Full Changelog](https://github.com/spritz/spritz/compare/v0.34...v0.35)

Changes in this release:

* Update the `org.realityforge.akasha` artifacts to version `0.28`.
* Update the `org.realityforge.arez` artifacts to version `0.200`.

### [v0.34](https://github.com/realityforge/router-fu/tree/v0.34) (2021-07-27) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.33...v0.34)

* Upgrade the `org.realityforge.arez` artifacts to version `0.198`.

### [v0.33](https://github.com/realityforge/router-fu/tree/v0.33) (2021-07-26) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.32...v0.33)

* Upgrade the `org.realityforge.akasha` artifacts to version `0.15`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.197`.
* Upgrade the `org.realityforge.braincheck` artifact to version `1.31.0`.
* Upgrade the `org.realityforge.akasha` artifact to version `0.10`.

### [v0.32](https://github.com/realityforge/router-fu/tree/v0.32) (2021-03-25) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.31...v0.32)

* Upgrade the `org.realityforge.grim` artifacts to version `0.05`.
* Upgrade the `au.com.stocksoftware.idea.codestyle` artifact to version `1.17`.
* Upgrade the `org.realityforge.org.jetbrains.annotations` artifact to version `1.7.0`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.193`.
* Migrate from using Elemental2 to Akasha when interacting with the Browser API.

### [v0.31](https://github.com/realityforge/router-fu/tree/v0.31) (2020-06-23) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.30...v0.31)

* Update the `HashBackend` so that visiting the empty path does not create an additional history element and thus back button works as expected.

### [v0.30](https://github.com/realityforge/router-fu/tree/v0.30) (2020-06-19) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.29...v0.30)

* Upgrade the `com.squareup:javapoet` artifact to version `1.13.0`.
* Update the annotation processor so that the hash suffixes like `"#/"`, `"#"` and `""` are treated as an empty path.
* Fix a bug that would stop programatic navigations to the empty path from triggering route updates.

### [v0.29](https://github.com/realityforge/router-fu/tree/v0.29) (2020-06-18) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.28...v0.29)

* Remove the `@Nonnull` annotations added to void methods in generated code.

### [v0.28](https://github.com/realityforge/router-fu/tree/v0.28) (2020-06-16) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.27...v0.28)

* Upgrade the `org.realityforge.arez` artifacts to version `0.182`.
* Upgrade the `org.realityforge.braincheck` artifact to version `1.29.0`.
* Constraints on route parameters have been fixed so that they are matched against the entire parameter value.

### [v0.27](https://github.com/realityforge/router-fu/tree/v0.27) (2020-05-29) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.26...v0.27)

* Upgrade the `org.realityforge.arez` artifacts to version `0.181`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.51`.
* Set the `requireId=DISABLE` and `disposeNotifier=DISABLE` parameters on the `@ArezComponent` annotation for generated routers that are arez enabled. The intention is to reduce code size by eliminating unused code.

### [v0.26](https://github.com/realityforge/router-fu/tree/v0.26) (2020-05-19) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.25...v0.26)

* Upgrade the `org.realityforge.braincheck` artifact to version `1.28.0`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.178`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.48`.
* Update code generation to follow the latest arez best practices.

### [v0.25](https://github.com/realityforge/router-fu/tree/v0.25) (2020-03-11) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.24...v0.25)

* Upgrade the `org.realityforge.braincheck` artifact to version `1.26.0`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.170`.
* Upgrade the `org.realityforge.org.jetbrains.annotations` artifact to version `1.5.0`.
* Upgrade the `org.realityforge.grim` artifacts to version `0.04`.
* Upgrade the `com.google.guava` artifact to version `27.1-jre`.
* Upgrade the `com.google.truth` artifact to version `0.45`.
* Upgrade the `com.google.testing.compile` artifact to version `0.18-rf`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.41`.
* Upgrade the `org.realityforge.guiceyloops` artifact to version `0.106`.
* Change constructor argument in the generated router from accepting a `window` object to accepting a `router.Backend`. This makes it possible to support arbitrary backends rather than just the `HashBackend`. This is a breaking change.

### [v0.24](https://github.com/realityforge/router-fu/tree/v0.24) (2020-01-16) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.23...v0.24)

* Upgrade the `com.squareup:javapoet` artifact to version `1.12.0`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.16`.
* Expose the `Route.getName()` helper method.

### [v0.23](https://github.com/realityforge/router-fu/tree/v0.23) (2020-01-13) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.22...v0.23)

* Upgrade the `org.realityforge.proton` artifacts to version `0.14`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.165`.

### [v0.22](https://github.com/realityforge/router-fu/tree/v0.22) (2020-01-06) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.21...v0.22)

* Upgrade the `org.realityforge.arez` artifacts to version `0.162`.
* Use the `org.realityforge.proton` project for processor utility methods.
* Shade the `auto-common` artifact in the annotation processor.
* Use builtin `SourceVersion.isIdentifier(String)` method rather than custom code that emulates behaviour.
* When generating the router, stop copying all `@Documented` annotated annotations and instead copy the specific set of whitelisted annotations that developers typically care about. These include the nullability annotations `@Nonnull` and `@Nullable` as well ass `@Deprecated`.
* Decouple from `com.google.auto.service:auto-service` by defining service manually.
* Optimize the annotation processor by indicating the specific annotation that is processed in the list of supported annotations types.
* Change the name of the generated class when processing a nested class to follow the same conventions as Arez, Dagger, React4j etc. Namely the generated classname components are separated by `_` rather than `$`.
* Make sure that the generated classes can extend parameterized types without warnings.
* Decouple the `processor` artifact from the `com.google.auto:auto-common` dependency and thus the `com.google.guava:guava` dependency. This significantly reduces the build time for the processor and the size of the processor artifact.

### [v0.21](https://github.com/realityforge/router-fu/tree/v0.21) (2019-12-17) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.20...v0.21)

* Upgrade the `org.realityforge.arez` artifacts to version `0.159`.

### [v0.20](https://github.com/realityforge/router-fu/tree/v0.20) (2019-12-06) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.19...v0.20)

* Upgrade the `org.realityforge.com.google.elemental2` artifacts to version `2.27`.
* Upgrade the `org.realityforge.org.jetbrains.annotations` artifact to version `1.2.0`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.156`.
* Stop generating routers that use the `nameIncludesId` annotation parameter on the `@ArezComponent` annotation as it is scheduled for removal.

### [v0.19](https://github.com/realityforge/router-fu/tree/v0.19) (2019-10-17) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.18...v0.19)

* Upgrade the `org.realityforge.arez` artifacts to version `0.150`.
* Upgrade the `org.realityforge.braincheck` artifact to version `1.25.0`.
* Upgrade the `org.realityforge.javax.annotation` artifact to version `1.0.1`.
* Upgrade the `org.realityforge.com.google.elemental2` artifacts to version `2.25`.
* Add the `org.realityforge.grim` dependency required for arez.

### [v0.18](https://github.com/realityforge/router-fu/tree/v0.18) (2019-07-17) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.17...v0.18)

* Upgrade the `org.realityforge.guiceyloops` artifact to version `0.102`.
* Upgrade the `au.com.stocksoftware.idea.codestyle` artifact to version `1.14`.
* Upgrade the `org.realityforge.arez` artifacts to version `0.143`.
* Upgrade the `org.realityforge.braincheck` artifact to version `1.20.0`.
* Upgrade the `org.realityforge.com.google.jsinterop` artifact to version `1.0.0-b2-e6d791f`.
* Upgrade the `org.realityforge.com.google.elemental2` artifacts to version `2.24`.
* Remove `{@inheritDoc}` as it only explicitly indicates that the default behaviour at the expense of significant visual clutter.
* Fix bug where `#` is not removed when routing to empty location.

### [v0.17](https://github.com/realityforge/router-fu/tree/v0.17) (2019-02-04) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.16...v0.17)

* Upgrade Elemental2 artifacts to groupId `org.realityforge.com.google.elemental2`
  and version `1.0.0-b14-2f97dbe`. This makes it possible to use a newer version of the
  Elemental2 library in downstream products.
* 💥 **\[core\]** Upgrade Arez to version `0.127`.

### [v0.16](https://github.com/realityforge/router-fu/tree/v0.16) (2018-12-18) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.15...v0.16)

* Remove deployment from TravisCI infrastructure as it is no longer feasible.
* **\[processor\]** Allow uri encoding of parameters.

### [v0.15](https://github.com/realityforge/router-fu/tree/v0.15) (2018-11-20) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.14...v0.15)

* 💥 **\[core\]** Upgrade Arez to version `0.115`.
* **\[core\]** Upgrade `org.realityforge.braincheck:braincheck:jar` to version `1.12.0`.
* **\[processor\]** Upgrade javapoet to version `1.11.1`.

### [v0.14](https://github.com/realityforge/router-fu/tree/v0.14) (2018-08-23) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.13...v0.14)

* 💥 **\[core\]** Upgrade Arez to version `0.105`.
* 💥 Merge the `router-fu-annotations` artifact into `router-fu-core` to simplify usage.

### [v0.13](https://github.com/realityforge/router-fu/tree/v0.13) (2018-07-12) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.12...v0.13)

* 💥 **\[core\]** Upgrade Arez to version `0.97`.
* **\[processor\]** Stop annotating the generated `build*Location(...)` methods with
  `@arez.annotations.Action` as they do not read or write arez state and thus it is
  not needed.

### [v0.12](https://github.com/realityforge/router-fu/tree/v0.12) (2018-07-06) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.11...v0.12)

* 💥 **\[core\]** Upgrade Arez to version `0.96`.
* Ensure that all `BrainCheck` invariant calls are contained within a guard such as
  `if ( BrainCheckConfig.checkApiInvariants() ) { ... }`. This ensures that GWT2.x is
  able to perform dead code elimination, including removing the class literals for the
  lambdas.

### [v0.11](https://github.com/realityforge/router-fu/tree/v0.11) (2018-06-18) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.10...v0.11)

* Remove dependency on anodoc library.
* 💥 **\[core\]** Upgrade Arez to version `0.92`.
* Replace the `javax.annotation` implementation with a J2CL compatible variant.

### [v0.10](https://github.com/realityforge/router-fu/tree/v0.10) (2018-05-21) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.09...v0.10)

* 💥 **\[core\]** Upgrade Arez to version `0.84`.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:base` library to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade `com.google.elemental2:*` libraries to version `1.0.0-beta-3`.
* Fix bug that resulted in inclusion in the package of the gwt compile output.

### [v0.09](https://github.com/realityforge/router-fu/tree/v0.09) (2018-04-06) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.08...v0.09)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.75`.
* 💥 **\[core\]** Upgrade BrainCheck library to version `1.5.0`.
* **\[processor\]** Improve the generation of switch statement in router so it is easier to read and
  so that findbugs does not generate warnings.

### [v0.08](https://github.com/realityforge/router-fu/tree/v0.08) (2018-02-28) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.07...v0.08)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.60`.
* 💥 **\[core\]** Upgrade BrainCheck library to version `1.4.0`.

### [v0.07](https://github.com/realityforge/router-fu/tree/v0.07) (2018-01-26) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.06...v0.07)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.44`.

### [v0.06](https://github.com/realityforge/router-fu/tree/v0.06) (2018-01-25) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.05...v0.06)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.43`.

### [v0.05](https://github.com/realityforge/router-fu/tree/v0.05) (2018-01-12) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.04...v0.05)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.42`.

### [v0.04](https://github.com/realityforge/router-fu/tree/v0.04) (2017-12-11) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.03...v0.04)

##### Changed
* Upgrade Buildr to version 1.5.4.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:jsinterop-annotations` library to version `1.0.2`.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:base` library to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade `com.google.elemental2:*` libraries to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade Arez library to version `0.34`.

### [v0.03](https://github.com/realityforge/router-fu/tree/v0.03) (2017-11-29) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.02...v0.03)

##### Changed
* Upgrade Arez to version 0.30.
* **\[processor\]** Shade the processor dependencies so that the only jar required during annotation processing
  is the annotation processor jar. This eliminates the possibility of processorpath conflicts causing issues in
  the future.
* **\[processor\]** Upgrade javapoet to version 1.8.0.
* Remove unused dependency `org.jetbrains:annotations:jar:15.0`
* Remove unused dependency `org.mockito:mockito-all:jar:1.10.19`
* **\[processor\]** Remove the direct dependency on the `javax.annotation.Nonnull` and
  `javax.annotation.Nullable` annotations from the processor module.
* **\[processor\]** Remove the direct dependency on the `router.fu` and `router.fu.annotations` packages from the
  processor module.

### [v0.02](https://github.com/realityforge/router-fu/tree/v0.02) (2017-11-21) · [Full Changelog](https://github.com/realityforge/router-fu/compare/v0.01...v0.02)

##### Fixed
* **\[processor\]** Routers that are Arez enabled, implement the `preDispose()` method that removes the listener
  prior to disposing the Arez elements.

### [v0.01](https://github.com/realityforge/router-fu/tree/v0.01) (2017-11-21) · [Full Changelog](https://github.com/realityforge/router-fu/compare/16c08581b5f53aee939383cccd04a9c12c00384e...v0.01)

 ‎🎉	Initial super-alpha release ‎🎉.
