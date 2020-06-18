package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithNonTargetParametersService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionFilterRoute();

  @Nullable
  RouteState getRegionFilterRouteState();

  @Nonnull
  Parameter getRegionFilterRegionCodeParameter();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Parameter getRegionRegionCodeParameter();

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  void gotoRegion(@Nonnull String regionCode);

  void reRoute();
}
