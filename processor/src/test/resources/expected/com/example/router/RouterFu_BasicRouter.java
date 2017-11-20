package com.example.router;

import elemental2.dom.Window;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.HashBackend;
import router.fu.Location;
import router.fu.RouteState;
import router.fu.Router;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_BasicRouter extends BasicRouter implements BasicRouterService {
  private final Router $fu$_router;

  private Location $fu$_location;

  RouterFu_BasicRouter(@Nonnull final Window window) {
    $fu$_router = new Router( this::onLocationChanged, new HashBackend( window ), Collections.unmodifiableList( Arrays.asList(  ) ) );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(@Nonnull final Location location) {
    $fu$_location = location;
  }

  void onLocationChanged(@Nonnull final Location location) {
    setLocation( Objects.requireNonNull( location ) );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 0; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
      }
    }
  }
}
