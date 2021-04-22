package router.fu.example;

import akasha.Console;
import akasha.Document;
import akasha.WindowGlobal;
import arez.Arez;
import com.google.gwt.core.client.EntryPoint;
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

    final Document document = WindowGlobal.document();
    document.querySelector( "#login" ).
      addClickListener( e -> Arez.context().safeAction( () -> AppState.AUTH.setUsername( "Bob" ) ) );
    document.querySelector( "#logout" ).
      addClickListener( e -> Arez.context().safeAction( () -> AppState.AUTH.setUsername( null ) ) );
    document.querySelector( "#route_regions" ).
      addClickListener( e -> AppState.ROUTER.gotoRegions() );
    document.querySelector( "#route_region" ).
      addClickListener( e -> AppState.ROUTER.gotoRegion( "ballarat" ) );
    document.querySelector( "#route_regionEvents" ).
      addClickListener( e -> AppState.ROUTER.gotoRegionEvents( "ballarat" ) );
    document.querySelector( "#route_regionEvent" ).
      addClickListener( e -> AppState.ROUTER.gotoRegionEvent( "ballarat", "42" ) );
    document.querySelector( "#randomize_regionCode" ).
      addClickListener( e -> AppState.ROUTER.updateRegionCode( String.valueOf( new Random().nextInt() ) ) );
    document.querySelector( "#randomize_eventId" ).
      addClickListener( e -> AppState.ROUTER.updateEventId( String.valueOf( new Random().nextInt() ) ) );
    Arez.context().observer( this::renderView );
  }

  private void renderView()
  {
    final Document document = WindowGlobal.document();
    final String username = AppState.AUTH.getUsername();
    document.querySelector( "#username" ).innerHTML = null == username ? "" : username;
    document.querySelector( "#location" ).innerHTML = AppState.ROUTER.getLocation().getPath();
    final String regionCode = AppState.ROUTER.getRegionCode();
    document.querySelector( "#regionCode" ).innerHTML = null == regionCode ? "" : regionCode;
    final String eventId = AppState.ROUTER.getEventId();
    document.querySelector( "#eventId" ).innerHTML = null == eventId ? "" : eventId;

    final RouteState state = AppState.ROUTER.getRegionFilterRouteState();
    if ( null != state )
    {
      final String regionCode2 = state.getParameterValue( AppState.ROUTER.getRegionFilterRegionCodeParameter() );
      Console.log( "RegionCode: " + regionCode2 );
    }
    else
    {
      Console.log( "No RegionCode" );
    }
  }
}
