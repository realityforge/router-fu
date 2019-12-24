package com.example.parameter;

import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "other", path = "regions/:regionCode" )
@BoundParameter( name = "regionCode", routeNames = { "region" } )
public class NoSuchRoute
{
}
