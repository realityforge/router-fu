package com.example.callback;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@NullMarked
public class CallbackFromInterface
  implements CallbackInterface
{
}
