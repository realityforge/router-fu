package com.example.parameter;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface MultiRouteBoundParameterService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Route getRegionEventsRoute();

  @Nullable
  RouteState getRegionEventsRouteState();

  @Nonnull
  Route getRegionEventRoute();

  @Nullable
  RouteState getRegionEventRouteState();

  @Nullable
  String getRegionCode();

  void updateRegionCode(@Nonnull String regionCode);

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
}