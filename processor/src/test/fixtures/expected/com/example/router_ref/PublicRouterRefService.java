package com.example.router_ref;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface PublicRouterRefService {
  @Nonnull
  Location getLocation();

  void reRoute();
}
