package com.example.route;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithOptionalParametersService {
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
  Parameter getRegionEventsFilterParameter();

  @Nonnull
  Route getRegionEventRoute();

  @Nullable
  RouteState getRegionEventRouteState();

  @Nonnull
  Parameter getRegionEventRegionCodeParameter();

  @Nonnull
  Parameter getRegionEventEventIdParameter();

  @Nonnull
  Parameter getRegionEventFilterParameter();

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  void gotoRegion(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventsLocation(@Nonnull String regionCode, @Nonnull String filter);

  void gotoRegionEvents(@Nonnull String regionCode, @Nonnull String filter);

  @Nonnull
  String buildRegionEventLocation(@Nonnull String regionCode, @Nonnull String eventId,
      @Nonnull String filter);

  void gotoRegionEvent(@Nonnull String regionCode, @Nonnull String eventId, @Nonnull String filter);

  void reRoute();
}
