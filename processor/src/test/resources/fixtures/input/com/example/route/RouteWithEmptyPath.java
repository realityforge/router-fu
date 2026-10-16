package com.example.route;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "welcome", path = "" )
@Route(name = "filter", path = "", navigationTarget = false, partialMatch = true)
@NullMarked
public class RouteWithEmptyPath
{
}
