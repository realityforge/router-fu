# Change Log

### Unreleased

* Upgrade the `com.squareup:javapoet` artifact to version `1.12.0`.
* Upgrade the `org.realityforge.proton` artifacts to version `0.16`.

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
