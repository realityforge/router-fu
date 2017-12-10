# Change Log

### Unreleased

##### Changed
* Upgrade Buildr to version 1.5.4.
* ð¥ **\[core\]** Upgrade `com.google.jsinterop:jsinterop-annotations` library to version `1.0.2`.
* ð¥ **\[core\]** Upgrade Arez library to version `0.34`.
* ð¥ **\[core\]** Upgrade `com.google.elemental2:*` libraries to version `1.0.0-beta-3`.

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
