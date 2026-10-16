package com.example.route;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface RouteParametersWithShortNameService {
  Location getLocation();

  Route getARoute();

  @Nullable RouteState getARouteState();

  Parameter getARegionCodeParameter();

  String buildALocation(String regionCode);

  void gotoA(String regionCode);

  void reRoute();
}
