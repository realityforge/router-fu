package com.example.parameter;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface NonDefaultNameParameterService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Parameter getRegionRegionCodeParameter();

  @Nullable
  String getFoo();

  void updateFoo(@Nonnull String foo);

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  void gotoRegion(@Nonnull String regionCode);

  void reRoute();
}
