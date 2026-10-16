package com.example.nested;

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
public class NestedRouter_RouterFu_InnerRouter extends NestedRouter.InnerRouter
    implements NestedRouter_InnerRouterService {
  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  NestedRouter_RouterFu_InnerRouter(final Backend backend) {
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

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
