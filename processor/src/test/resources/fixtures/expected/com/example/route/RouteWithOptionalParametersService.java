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
public interface RouteWithOptionalParametersService {
  Location getLocation();

  Route getRegionRoute();

  @Nullable RouteState getRegionRouteState();

  Parameter getRegionRegionCodeParameter();

  Route getRegionEventsRoute();

  @Nullable RouteState getRegionEventsRouteState();

  Parameter getRegionEventsRegionCodeParameter();

  Parameter getRegionEventsFilterParameter();

  Route getRegionEventRoute();

  @Nullable RouteState getRegionEventRouteState();

  Parameter getRegionEventRegionCodeParameter();

  Parameter getRegionEventEventIdParameter();

  Parameter getRegionEventFilterParameter();

  String buildRegionLocation(String regionCode);

  void gotoRegion(String regionCode);

  String buildRegionEventsLocation(String regionCode, String filter);

  void gotoRegionEvents(String regionCode, String filter);

  String buildRegionEventLocation(String regionCode, String eventId, String filter);

  void gotoRegionEvent(String regionCode, String eventId, String filter);

  void reRoute();
}
