package com.example.arez;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import router.fu.Location;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface ArezRouterService {
  Location getLocation();

  void reRoute();
}
