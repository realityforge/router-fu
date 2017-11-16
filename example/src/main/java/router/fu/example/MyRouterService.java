package router.fu.example;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.arez.annotations.Action;
import org.realityforge.arez.annotations.Observable;
import router.fu.Location;
import router.fu.Route;
import router.fu.RouteState;

/**
 * Possible shape of generated router interface.
 */
@Generated( "router.fu" )
public interface MyRouterService
{
  @Action( mutation = false )
  @Nonnull
  String buildRegionsLocation();

  void gotoRegions();

  @Action( mutation = false )
  @Nonnull
  String buildRegionLocation( @Nonnull String regionCode );

  void gotoRegion( @Nonnull String regionCode );

  @Action( mutation = false )
  @Nonnull
  String buildRegionEventsLocation( @Nonnull String regionCode );

  void gotoRegionEvents( @Nonnull String regionCode );

  @Action( mutation = false )
  @Nonnull
  String buildRegionEventLocation( @Nonnull String regionCode, @Nonnull String eventId );

  void gotoRegionEvent( @Nonnull String regionCode, @Nonnull String eventId );

  @Observable
  @Nullable
  String getRegionCode();

  void setRegionCode( @Nonnull String regionCode );

  @Observable
  @Nullable
  String getEventId();

  void setEventId( @Nonnull String eventId );

  @Observable
  @Nonnull
  Location getLocation();

  @Observable
  @Nullable
  RouteState getAuthFilterRouteState();

  @Observable
  @Nullable
  RouteState getRegionsRouteState();

  @Observable
  @Nullable
  RouteState getRegionFilterRouteState();

  @Observable
  @Nullable
  RouteState getRegionRouteState();

  @Observable
  @Nullable
  RouteState getRegionEventsRouteState();

  @Observable
  @Nullable
  RouteState getRegionEventRouteState();

  @Nonnull
  Route getAuthFilterRoute();

  @Nonnull
  Route getRegionsRoute();

  @Nonnull
  Route getRegionFilterRoute();

  @Nonnull
  Route getRegionRoute();

  @Nonnull
  Route getRegionEventsRoute();

  @Nonnull
  Route getRegionEventRoute();
}
