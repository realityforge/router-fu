package com.example.route;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithEmptyPathService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getWelcomeRoute();

  @Nullable
  RouteState getWelcomeRouteState();

  @Nonnull
  String buildWelcomeLocation();

  void gotoWelcome();

  void reRoute();
}
