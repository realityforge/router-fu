package com.example.route;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "A", path = "regions/:regionCode" )
@NullMarked
public class RouteParametersWithShortName
{
}
