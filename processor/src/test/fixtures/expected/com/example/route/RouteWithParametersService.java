package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithParametersService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Parameter getRegionRegionCodeParameter();

  @Nonnull
  Route getRegionEventsRoute();

  @Nullable
  RouteState getRegionEventsRouteState();

  @Nonnull
  Parameter getRegionEventsRegionCodeParameter();

  @Nonnull
  Route getRegionEventRoute();

  @Nullable
  RouteState getRegionEventRouteState();

  @Nonnull
  Parameter getRegionEventRegionCodeParameter();

  @Nonnull
  Parameter getRegionEventEventCodeParameter();

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  void gotoRegion(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventsLocation(@Nonnull String regionCode);

  void gotoRegionEvents(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventLocation(@Nonnull String regionCode, @Nonnull String eventCode);

  void gotoRegionEvent(@Nonnull String regionCode, @Nonnull String eventCode);

  void reRoute();
}
