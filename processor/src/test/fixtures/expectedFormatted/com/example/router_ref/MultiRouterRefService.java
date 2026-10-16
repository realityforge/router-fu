package com.example.router_ref;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import router.fu.Location;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface MultiRouterRefService {
  Location getLocation();

  void reRoute();
}
