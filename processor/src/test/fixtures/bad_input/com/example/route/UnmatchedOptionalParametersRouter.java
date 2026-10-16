package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "root", path = "/some/path/:foo", optionalParameters = { "z", "a" } )
@org.jspecify.annotations.NullMarked
public class UnmatchedOptionalParametersRouter
{
}
