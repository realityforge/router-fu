package com.example.parameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface BasicBoundParameterService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Parameter getRegionRegionCodeParameter();

  @Nullable
  String getRegionCode();

  void updateRegionCode(@Nonnull String regionCode);

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  void gotoRegion(@Nonnull String regionCode);

  void reRoute();
}
