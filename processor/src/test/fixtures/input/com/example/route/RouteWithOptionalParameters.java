package com.example.route;

import router.fu.annotations.Route;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode", optionalParameters = { "regionCode" } )
@Route(
  name = "regionEvents",
  path = "regions/:regionCode/events/:filter",
  optionalParameters = { "regionCode", "filter" }
)
@Route(
  name = "regionEvent",
  path = "regions/:regionCode/event/:eventId/:filter",
  optionalParameters = { "regionCode", "filter" }
)
public class RouteWithOptionalParameters
{
}
