package com.example.route;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteParametersWithShortNameService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getARoute();

  @Nullable
  RouteState getARouteState();

  @Nonnull
  Parameter getARegionCodeParameter();

  @Nonnull
  String buildALocation(@Nonnull String regionCode);

  void gotoA(@Nonnull String regionCode);

  void reRoute();
}
