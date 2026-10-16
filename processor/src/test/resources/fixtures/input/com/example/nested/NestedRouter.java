package com.example.nested;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Router;

@NullMarked
public final class NestedRouter
{
  private NestedRouter()
  {
  }

  @Router
  public static class InnerRouter
  {
  }
}
