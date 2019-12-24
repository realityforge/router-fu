package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class BadType2RouterRef
{
  @RouterRef
  String getRouterService()
  {
    throw new IllegalStateException();
  }
}
