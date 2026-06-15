package com.example.router;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface BasicRouterService {
  @Nonnull
  Location getLocation();

  void reRoute();
}
