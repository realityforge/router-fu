package com.example.parameter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@Generated("router.fu.processor.RouterProcessor")
public interface URIEncodedParameterService {
  @Nonnull
  Location getLocation();

  @Nonnull
  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  @Nonnull
  Parameter getRegionKeywordParameter();

  @Nullable
  String getKeyword();

  void updateKeyword(@Nonnull String keyword);

  @Nonnull
  String buildRegionLocation(@Nonnull String keyword);

  void gotoRegion(@Nonnull String keyword);

  void reRoute();
}
