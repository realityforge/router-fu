package com.example.nested;

import javax.annotation.Nonnull;
import javax.annotation.processing.Generated;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface NestedRouter_InnerRouterService {
  @Nonnull
  Location getLocation();

  void reRoute();
}
