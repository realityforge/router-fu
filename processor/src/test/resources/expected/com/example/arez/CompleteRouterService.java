package com.example.arez;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.annotations.Observable;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface CompleteRouterService {
  @Nonnull
  @Observable
  Location getLocation();

  @Observable
  @Nonnull
  Route getRegionFilterRoute();

  @Observable
  @Nullable
  RouteState getRegionFilterRouteState();

  @Observable
  @Nonnull
  Route getRegionRoute();

  @Observable
  @Nullable
  RouteState getRegionRouteState();

  @Observable
  @Nonnull
  Route getRegionEventsRoute();

  @Observable
  @Nullable
  RouteState getRegionEventsRouteState();

  @Observable
  @Nonnull
  Route getRegionEventRoute();

  @Observable
  @Nullable
  RouteState getRegionEventRouteState();

  @Observable
  @Nullable
  String getRegionCode();

  @Observable
  @Nullable
  String getEventCode();

  @Observable
  @Nullable
  String getEventCode2();

  @Nonnull
  @Action(
      mutation = false
  )
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);

  @Nonnull
  @Action(
      mutation = false
  )
  String buildRegionEventsLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegionEvents(@Nonnull String regionCode);

  @Nonnull
  @Action(
      mutation = false
  )
  String buildRegionEventLocation(@Nonnull String regionCode, @Nonnull String eventCode);

  @Nonnull
  void gotoRegionEvent(@Nonnull String regionCode, @Nonnull String eventCode);
}
