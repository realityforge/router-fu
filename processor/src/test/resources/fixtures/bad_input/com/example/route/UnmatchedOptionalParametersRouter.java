package com.example.route;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "root", path = "/some/path/:foo", optionalParameters = { "z", "a" } )
@NullMarked
public class UnmatchedOptionalParametersRouter
{
}
