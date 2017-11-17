package com.example.parameter;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
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

  @Nullable
  String getRegionCode();

  void updateRegionCode(@Nonnull String regionCode);

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);
}
