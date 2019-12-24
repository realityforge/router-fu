package com.example.parameter;

import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@BoundParameter( name = "regionCode" )
@BoundParameter( name = "regionCode" )
public class DuplicateParameterName
{
}
