package router.fu.example;

import arez.Arez;
import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import java.util.Random;
import router.fu.RouteState;

public class Example
  implements EntryPoint
{
  @Override
  public void onModuleLoad()
  {
    // Need to force AppState clinit to be run to ensure that it is run as top level transaction
    @SuppressWarnings( "unused" )
    final MyRouterService router = AppState.ROUTER;

    DomGlobal.document.querySelector( "#login" ).
      addEventListener( "click", e -> Arez.context().safeAction( () -> AppState.AUTH.setUsername( "Bob" ) ) );
    DomGlobal.document.querySelector( "#logout" ).
      addEventListener( "click", e -> Arez.context().safeAction( () -> AppState.AUTH.setUsername( null ) ) );
    DomGlobal.document.querySelector( "#route_regions" ).
      addEventListener( "click", e -> AppState.ROUTER.gotoRegions() );
    DomGlobal.document.querySelector( "#route_region" ).
      addEventListener( "click", e -> AppState.ROUTER.gotoRegion( "ballarat" ) );
    DomGlobal.document.querySelector( "#route_regionEvents" ).
      addEventListener( "click", e -> AppState.ROUTER.gotoRegionEvents( "ballarat" ) );
    DomGlobal.document.querySelector( "#route_regionEvent" ).
      addEventListener( "click", e -> AppState.ROUTER.gotoRegionEvent( "ballarat", "42" ) );
    DomGlobal.document.querySelector( "#randomize_regionCode" ).
      addEventListener( "click", e -> AppState.ROUTER.updateRegionCode( String.valueOf( new Random().nextInt() ) ) );
    DomGlobal.document.querySelector( "#randomize_eventId" ).
      addEventListener( "click", e -> AppState.ROUTER.updateEventId( String.valueOf( new Random().nextInt() ) ) );
    Arez.context().autorun( this::renderView );
  }

  private void renderView()
  {
    final String username = AppState.AUTH.getUsername();
    DomGlobal.document.querySelector( "#username" ).innerHTML = null == username ? "" : username;
    DomGlobal.document.querySelector( "#location" ).innerHTML = AppState.ROUTER.getLocation().getPath();
    final String regionCode = AppState.ROUTER.getRegionCode();
    DomGlobal.document.querySelector( "#regionCode" ).innerHTML = null == regionCode ? "" : regionCode;
    final String eventId = AppState.ROUTER.getEventId();
    DomGlobal.document.querySelector( "#eventId" ).innerHTML = null == eventId ? "" : eventId;

    final RouteState state = AppState.ROUTER.getRegionFilterRouteState();
    if ( null != state )
    {
      final String regionCode2 = state.getParameterValue( AppState.ROUTER.getRegionFilterRegionCodeParameter() );
      DomGlobal.console.log( "RegionCode: " + regionCode2 );
    }
    else
    {
      DomGlobal.console.log( "No RegionCode" );
    }
  }
}
