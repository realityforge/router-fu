package com.example.route;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithEmptyPathService {
  Location getLocation();

  Route getWelcomeRoute();

  @Nullable RouteState getWelcomeRouteState();

  Route getFilterRoute();

  @Nullable RouteState getFilterRouteState();

  String buildWelcomeLocation();

  void gotoWelcome();

  void reRoute();
}
