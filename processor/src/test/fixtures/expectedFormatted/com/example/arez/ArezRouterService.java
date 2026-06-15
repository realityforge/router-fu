package com.example.arez;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface ArezRouterService {
  @Nonnull
  Location getLocation();

  void reRoute();
}
