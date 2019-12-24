package com.example.arez;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface CompleteRouterService {
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

  @Nullable
  String getRegionCode();

  void updateRegionCode(@Nonnull String regionCode);

  @Nullable
  String getEventCode();

  void updateEventCode(@Nonnull String eventCode);

  @Nullable
  String getEventCode2();

  void updateEventCode2(@Nonnull String eventCode2);

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventsLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegionEvents(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventLocation(@Nonnull String regionCode, @Nonnull String eventCode);

  @Nonnull
  void gotoRegionEvent(@Nonnull String regionCode, @Nonnull String eventCode);

  void reRoute();
}
