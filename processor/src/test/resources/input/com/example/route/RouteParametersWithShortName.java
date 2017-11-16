package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "A", path = "regions/:regionCode" )
public class RouteParametersWithShortName
{
}
