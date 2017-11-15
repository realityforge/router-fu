package router.fu.example;

import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.RouteLocation;
import router.fu.RouteState;

/**
 * Possible shape of generated router interface.
 */
@Generated( "router.fu" )
public interface MyRouterService
{
  @Nonnull
  String buildRegionsLocation();

  void gotoRegions();

  @Nonnull
  String buildRegionLocation( @Nonnull String regionCode );

  void gotoRegion( @Nonnull String regionCode );

  @Nonnull
  String buildRegionEventsLocation( @Nonnull String regionCode );

  void gotoRegionEvents( @Nonnull String regionCode );

  @Nonnull
  String buildRegionEventLocation( @Nonnull String regionCode, @Nonnull String eventId );

  void gotoRegionEvent( @Nonnull String regionCode, @Nonnull String eventId );

  @Nullable
  String getRegionCode();

  void setRegionCode( @Nonnull String regionCode );

  @Nullable
  String getEventId();

  void setEventId( @Nonnull String eventId );

  @Nonnull
  RouteLocation getLocation();

  @Nullable
  RouteState getAuthFilterRouteState();

  @Nullable
  RouteState getRegionsRouteState();

  @Nullable
  RouteState getRegionFilterRouteState();

  @Nullable
  RouteState getRegionRouteState();

  @Nullable
  RouteState getRegionEventsRouteState();

  @Nullable
  RouteState getRegionEventRouteState();
}
