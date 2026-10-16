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
public interface MultiRouteBoundParameterService {
  Location getLocation();

  Route getRegionRoute();

  @Nullable RouteState getRegionRouteState();

  Parameter getRegionRegionCodeParameter();

  Route getRegionEventsRoute();

  @Nullable RouteState getRegionEventsRouteState();

  Parameter getRegionEventsRegionCodeParameter();

  Route getRegionEventRoute();

  @Nullable RouteState getRegionEventRouteState();

  Parameter getRegionEventRegionCodeParameter();

  Parameter getRegionEventEventCodeParameter();

  @Nullable String getRegionCode();

  void updateRegionCode(String regionCode);

  String buildRegionLocation(String regionCode);

  void gotoRegion(String regionCode);

  String buildRegionEventsLocation(String regionCode);

  void gotoRegionEvents(String regionCode);

  String buildRegionEventLocation(String regionCode, String eventCode);

  void gotoRegionEvent(String regionCode, String eventCode);

  void reRoute();
}
