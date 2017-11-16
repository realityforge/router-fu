package com.example.route;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.annotations.Action;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface RouteWithPartialMatchParametersService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Route getRegionFilterRoute();

  @Nullable
  RouteState getRegionFilterRouteState();

  @Nonnull
  @Action(
      mutation = false
  )
  String buildRegionLocation(@Nonnull String regionCode);

  @Nonnull
  void gotoRegion(@Nonnull String regionCode);
}
