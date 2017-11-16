package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Location;
import router.fu.Route;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithNonTargetParametersService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nonnull
  Route getRegionFilterRoute();

  @Nonnull
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);
}
