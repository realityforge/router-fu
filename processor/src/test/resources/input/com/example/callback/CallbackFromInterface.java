package com.example.callback;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
public class CallbackFromInterface
  implements CallbackInterface
{
}
