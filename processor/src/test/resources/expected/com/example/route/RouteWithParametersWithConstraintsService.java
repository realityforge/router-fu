package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Location;
import router.fu.Route;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithParametersWithConstraintsService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionEventRoute();

  @Nonnull
  Route getRegionRoute();

  @Nonnull
  Route getRegionEventsRoute();

  @Nonnull
  String buildRegionEventLocation(@Nonnull String regionCode, @Nonnull String eventCode);

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  String buildRegionEventsLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegionEvent(@Nonnull String regionCode, @Nonnull String eventCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);

  @Nonnull
  void gotoRegionEvents(@Nonnull String regionCode);
}
