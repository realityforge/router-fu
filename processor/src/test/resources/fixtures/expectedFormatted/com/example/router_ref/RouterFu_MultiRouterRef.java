package com.example.router_ref;

import java.util.Arrays;
import java.util.Collections;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Backend;
import router.fu.Location;
import router.fu.Router;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_MultiRouterRef extends MultiRouterRef implements MultiRouterRefService {
  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  RouterFu_MultiRouterRef(final Backend backend) {
    $fu$_router =
        new Router(this::onLocationChanged, backend, Collections.unmodifiableList(Arrays.asList()));
    $fu$_router.activate();
  }

  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(final Location location) {
    $fu$_location = location;
  }

  void onLocationChanged(final Location location) {
    setLocation(location);
  }

  MultiRouterRefService getRouterService() {
    return this;
  }

  MultiRouterRefService getRouterService2() {
    return this;
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
