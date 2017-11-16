package com.example.nested;

import elemental2.dom.Window;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Elemental2HashBackend;
import router.fu.Location;
import router.fu.Router;

@Generated("router.fu.processor.RouterProcessor")
public class NestedRouter$RouterFu_InnerRouter extends NestedRouter.InnerRouter implements NestedRouter$InnerRouterService {
  private final Router $fu$_router;

  private Location $fu$_location;

  NestedRouter$RouterFu_InnerRouter(@Nonnull final Window window) {
    $fu$_router = new Router( this::onLocationChanged, new Elemental2HashBackend( window ), Collections.unmodifiableList( Arrays.asList(  ) ) );
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
  }
}
