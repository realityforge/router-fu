## TODO

### Medium Priority 

* Add mechanism for stripping trailing slash
* Figure out a way that if a re-render occurs and a callback is not invoked then dependencies of
  callback are cleared ... somehow.

### Low Priority 

* Support routes that dynamically load routes. i.e. Add a route that dynamically loads a set of routes and
  replaces itself with them.
* Support either child or peer routers. These routers pick up optional route data that are suffixed to the main
  route and delegated to separate components. Typically these will be things like popup dialogs that contain
  some state. This typically used to manage/expose user-interface state.

### Mobx Examples to validate against

* https://hackernoon.com/how-to-decouple-state-and-ui-a-k-a-you-dont-need-componentwillmount-cc90b787aa37

#### Mobx+Router5+React example

* https://vincent.is/testing-a-different-spa-routing/
* https://vincent.is/testing-a-different-spa-routing-update/
