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
public interface URIEncodedParameterService {
  Location getLocation();

  Route getRegionRoute();

  @Nullable RouteState getRegionRouteState();

  Parameter getRegionKeywordParameter();

  @Nullable String getKeyword();

  void updateKeyword(String keyword);

  String buildRegionLocation(String keyword);

  void gotoRegion(String keyword);

  void reRoute();
}
