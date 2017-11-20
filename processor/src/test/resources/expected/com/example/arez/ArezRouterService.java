package com.example.arez;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface ArezRouterService {
  @Nonnull
  Location getLocation();

  void reRoute();
}
