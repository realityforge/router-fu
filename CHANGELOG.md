# Change Log

### Unreleased

##### Changed
* Upgrade Arez to version 0.30.
* **\[processor\]** Shade the processor dependencies so that the only jar required during annotation processing
  is the annotation processor jar. This eliminates the possibility of processorpath conflicts causing issues in
  the future.
* **\[processor\]** Upgrade javapoet to version 1.8.0.

### [v0.02](https://github.com/realityforge/router-fu/tree/v0.02) (2017-11-21)
[Full Changelog](https://github.com/realityforge/router-fu/compare/v0.01...v0.02)

##### Fixed
* **\[processor\]** Routers that are Arez enabled, implement the `preDispose()` method that removes the listener
  prior to disposing the Arez elements.

### [v0.01](https://github.com/realityforge/router-fu/tree/v0.01) (2017-11-21)
[Full Changelog](https://github.com/realityforge/router-fu/compare/16c08581b5f53aee939383cccd04a9c12c00384e...v0.01)

 ‎🎉	Initial super-alpha release ‎🎉.
