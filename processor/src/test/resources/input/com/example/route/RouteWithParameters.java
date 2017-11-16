package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@Route( name = "regionEvents", path = "regions/:regionCode/events" )
@Route( name = "regionEvent", path = "regions/:regionCode/events/:eventCode" )
public class RouteWithParameters
{
}
