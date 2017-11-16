package com.example.arez;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import org.realityforge.arez.annotations.Observable;
import router.fu.Location;

@Generated("router.fu.processor.RouterProcessor")
public interface ArezRouterService {
  @Nonnull
  @Observable
  Location getLocation();
}
