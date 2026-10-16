package com.example.parameter;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Location;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface NonDefaultNameParameterService {
  Location getLocation();

  Route getRegionRoute();

  @Nullable
  RouteState getRegionRouteState();

  Parameter getRegionRegionCodeParameter();

  @Nullable
  String getFoo();

  void updateFoo(String foo);

  String buildRegionLocation(String regionCode);

  void gotoRegion(String regionCode);

  void reRoute();
}
