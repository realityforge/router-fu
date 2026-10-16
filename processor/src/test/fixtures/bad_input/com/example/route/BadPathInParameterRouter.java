package com.example.route;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "root", path = "<>#kjskaj" )
@NullMarked
public class BadPathInParameterRouter
{
}
