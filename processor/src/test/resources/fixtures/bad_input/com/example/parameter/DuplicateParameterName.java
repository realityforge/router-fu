package com.example.parameter;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@BoundParameter( name = "regionCode" )
@BoundParameter( name = "regionCode" )
@NullMarked
public class DuplicateParameterName
{
}
