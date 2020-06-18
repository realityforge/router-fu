package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithLongStaticSegmentService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  String buildRegionLocation();

  void gotoRegion();

  void reRoute();
}
