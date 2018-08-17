# Change Log

### Unreleased

* 💥 **\[core\]** Upgrade Arez to version `0.105`.
* 💥 Merge the `router-fu-annotations` artifact into `router-fu-core` to simplify usage.

### [v0.13](https://github.com/realityforge/router-fu/tree/v0.13) (2018-07-12)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.12...v0.13)

* 💥 **\[core\]** Upgrade Arez to version `0.97`.
* **\[processor\]** Stop annotating the generated `build*Location(...)` methods with
  `@arez.annotations.Action` as they do not read or write arez state and thus it is
  not needed.

### [v0.12](https://github.com/realityforge/router-fu/tree/v0.12) (2018-07-06)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.11...v0.12)

* 💥 **\[core\]** Upgrade Arez to version `0.96`.
* Ensure that all `BrainCheck` invariant calls are contained within a guard such as
  `if ( BrainCheckConfig.checkApiInvariants() ) { ... }`. This ensures that GWT2.x is
  able to perform dead code elimination, including removing the class literals for the
  lambdas.

### [v0.11](https://github.com/realityforge/router-fu/tree/v0.11) (2018-06-18)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.10...v0.11)

* Remove dependency on anodoc library.
* 💥 **\[core\]** Upgrade Arez to version `0.92`.
* Replace the `javax.annotation` implementation with a J2CL compatible variant.

### [v0.10](https://github.com/realityforge/router-fu/tree/v0.10) (2018-05-21)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.09...v0.10)

* 💥 **\[core\]** Upgrade Arez to version `0.84`.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:base` library to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade `com.google.elemental2:*` libraries to version `1.0.0-beta-3`.
* Fix bug that resulted in inclusion in the package of the gwt compile output.

### [v0.09](https://github.com/realityforge/router-fu/tree/v0.09) (2018-04-06)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.08...v0.09)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.75`.
* 💥 **\[core\]** Upgrade BrainCheck library to version `1.5.0`.
* **\[processor\]** Improve the generation of switch statement in router so it is easier to read and
  so that findbugs does not generate warnings.

### [v0.08](https://github.com/realityforge/router-fu/tree/v0.08) (2018-02-28)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.07...v0.08)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.60`.
* 💥 **\[core\]** Upgrade BrainCheck library to version `1.4.0`.

### [v0.07](https://github.com/realityforge/router-fu/tree/v0.07) (2018-01-26)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.06...v0.07)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.44`.

### [v0.06](https://github.com/realityforge/router-fu/tree/v0.06) (2018-01-25)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.05...v0.06)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.43`.

### [v0.05](https://github.com/realityforge/router-fu/tree/v0.05) (2018-01-12)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.04...v0.05)

##### Changed
* 💥 **\[core\]** Upgrade Arez to version `0.42`.

### [v0.04](https://github.com/realityforge/router-fu/tree/v0.04) (2017-12-11)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.03...v0.04)

##### Changed
* Upgrade Buildr to version 1.5.4.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:jsinterop-annotations` library to version `1.0.2`.
* 💥 **\[core\]** Upgrade `com.google.jsinterop:base` library to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade `com.google.elemental2:*` libraries to version `1.0.0-beta-3`.
* 💥 **\[core\]** Upgrade Arez library to version `0.34`.

### [v0.03](https://github.com/realityforge/router-fu/tree/v0.03) (2017-11-29)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.02...v0.03)

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

### [v0.02](https://github.com/realityforge/router-fu/tree/v0.02) (2017-11-21)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.01...v0.02)

##### Fixed
* **\[processor\]** Routers that are Arez enabled, implement the `preDispose()` method that removes the listener
  prior to disposing the Arez elements.

### [v0.01](https://github.com/realityforge/router-fu/tree/v0.01) (2017-11-21)
[Full Changelog](https://github.com/realityforge/router-fu/compare/16c08581b5f53aee939383cccd04a9c12c00384e...v0.01)

 ‎🎉	Initial super-alpha release ‎🎉.
