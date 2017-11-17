package com.example.parameter;

import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "root", path = "" )
@BoundParameter( name = "regionCode" )
public class NoSuchParameterInAnyRoute
{
}
