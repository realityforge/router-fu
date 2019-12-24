package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "regionFilter", path = "regions/:regionCode", navigationTarget = false, partialMatch = true)
@Route( name = "region", path = "regions/:regionCode" )
public class RouteWithPartialMatchParameters
{
}
