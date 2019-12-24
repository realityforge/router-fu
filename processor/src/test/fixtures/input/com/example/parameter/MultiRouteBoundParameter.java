package com.example.parameter;

import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode<[a-zA-Z]+>" )
@Route( name = "regionEvents", path = "regions/:regionCode<[a-zA-Z]+>/events" )
@Route( name = "regionEvent", path = "regions/:regionCode<[a-zA-Z]+>/events/:eventCode<[0-9]+>" )
@BoundParameter( name = "regionCode" )
public class MultiRouteBoundParameter
{
}
