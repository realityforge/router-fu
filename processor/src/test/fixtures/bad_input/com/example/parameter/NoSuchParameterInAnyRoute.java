package com.example.parameter;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "root", path = "" )
@BoundParameter( name = "regionCode" )
@NullMarked
public class NoSuchParameterInAnyRoute
{
}
