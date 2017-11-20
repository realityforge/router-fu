package router.fu.example;

import elemental2.dom.DomGlobal;
import javax.annotation.Nonnull;
import router.fu.MatchResult;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router( arez = true )
@Route( name = "login", path = "login" )
@Route( name = "logout", path = "logout" )
// This first route is a filter and matches all locations but can not be navigated to
@Route( name = "authFilter", path = "", navigationTarget = false, partialMatch = true )
@Route( name = "regions", path = "regions" )
// The next route is used so can add non-terminal routing state for all routes
@Route( name = "regionFilter", path = "regions/:regionCode", navigationTarget = false, partialMatch = true )
@Route( name = "region", path = "regions/:regionCode" )
@Route( name = "regionEvents", path = "regions/:regionCode/events" )
@Route( name = "regionEvent", path = "regions/:regionCode/events/:eventId" )
// Any route that has a regionCode parameter will have that parameter bound to a field. This assumes that
// All instances of the parameter correspond to the same value. If this value is in a terminal route and it is changed
// then the location will be update to reflect the change
@BoundParameter( name = "regionCode" )
// Only the route "regionEvent" will have a eventId parameter will have that parameter bound to a field
@BoundParameter( name = "eventId", routeNames = { "regionEvent" } )
class MyRouter
{
  @Nonnull
  static MyRouterService create()
  {
    return new Arez_RouterFu_MyRouter( DomGlobal.window );
  }

  @RouterRef
  MyRouterService getRouterService()
  {
    throw new IllegalStateException();
  }

  @RouteCallback
  MatchResult authFilterCallback()
  {
    final boolean authenticated = null != AppState.AUTH.getUsername();
    if ( authenticated )
    {
      return MatchResult.NON_TERMINAL;
    }
    else
    {
      getRouterService().gotoLogin();
      return MatchResult.TERMINAL;
    }
  }
}
