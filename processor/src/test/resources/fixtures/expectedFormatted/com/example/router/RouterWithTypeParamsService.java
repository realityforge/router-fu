package com.example.router;

import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import router.fu.Location;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public interface RouterWithTypeParamsService {
  Location getLocation();

  void reRoute();
}
