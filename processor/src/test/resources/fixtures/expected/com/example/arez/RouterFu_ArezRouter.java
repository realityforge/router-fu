package com.example.arez;

import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Feature;
import arez.annotations.Observable;
import arez.annotations.PostConstruct;
import arez.annotations.PreDispose;
import java.util.Arrays;
import java.util.Collections;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Backend;
import router.fu.Location;
import router.fu.Router;

@NullMarked
@ArezComponent(
    name = "ArezRouter",
    requireId = Feature.DISABLE,
    disposeNotifier = Feature.DISABLE
)
@Generated("router.fu.processor.RouterProcessor")
public abstract class RouterFu_ArezRouter extends ArezRouter implements ArezRouterService {
  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  RouterFu_ArezRouter(final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList(  ) ) );
    $fu$_location = new Location( "", Collections.emptyList() );
  }

  @PostConstruct
  void postConstruct() {
    $fu$_router.activate();
  }

  @PreDispose
  void preDispose() {
    $fu$_router.deactivate();
  }

  @Observable
  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(final Location location) {
    $fu$_location = location;
  }

  @Action
  void onLocationChanged(final Location location) {
    setLocation( location );
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
