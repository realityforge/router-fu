package com.example.router;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Router;

@NullMarked
public class NonStaticNestedRouter
{
  @Router
  public class InnerRouter
  {
  }
}
