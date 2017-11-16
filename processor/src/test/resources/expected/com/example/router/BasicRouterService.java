package com.example.router;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface BasicRouterService {
  @Nonnull
  Location getLocation();
}
