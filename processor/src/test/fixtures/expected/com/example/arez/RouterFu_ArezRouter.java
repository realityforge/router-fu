package com.example.arez;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import arez.annotations.PostConstruct;
import arez.annotations.PreDispose;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Backend;
import router.fu.Location;
import router.fu.RouteState;
import router.fu.Router;

@ArezComponent(
    name = "ArezRouter"
)
@Generated("router.fu.processor.RouterProcessor")
public abstract class RouterFu_ArezRouter extends ArezRouter implements ArezRouterService {
  private final Router $fu$_router;

  private Location $fu$_location;

  RouterFu_ArezRouter(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList(  ) ) );
    $fu$_location = new Location( "", Collections.emptyList() );
  }

  @PostConstruct
  final void postConstruct() {
    $fu$_router.activate();
  }

  @PreDispose
  final void preDispose() {
    $fu$_router.deactivate();
  }

  @Nonnull
  @Observable
  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(@Nonnull final Location location) {
    $fu$_location = location;
  }

  @Action
  void onLocationChanged(@Nonnull final Location location) {
    setLocation( Objects.requireNonNull( location ) );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 0; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        default: {
          break;
        }
      }
    }
  }

  @Override
  public final void reRoute() {
    $fu$_router.reRoute();
  }
}
