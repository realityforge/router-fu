package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "welcome", path = "" )
@Route(name = "filter", path = "", navigationTarget = false, partialMatch = true)
@org.jspecify.annotations.NullMarked
public class RouteWithEmptyPath
{
}
