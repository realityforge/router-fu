## TODO

* Add mechanisms such that parameters from `RouteState` can be mapped to class fields or observable fields.
  Ideally the route/location would be updated if the application changes these fields rather than requiring
  that the application explicitly change the location.
* Write an annotation processor that generates a `Router`.
* Support routes that dynamically load routes. i.e. Add a route that dynamically loads a set of routes and
  replaces itself with them.
* Support either child or peer routers. These routers pick up optional route data that are suffixed to the main
  route and delegated to separate components. Typically these will be things like popup dialogs that contain
  some state. This typically used to manage/expose user-interface state.
