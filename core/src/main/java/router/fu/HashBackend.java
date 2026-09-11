package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;
import jsinterop.base.Js;

/**
 * Hash based routing backend.
 */
public class HashBackend
  implements Backend
{
  @Nonnull
  private final JsRuntime.Window _window;

  /**
   * Create backend for routing for on current window.
   */
  public HashBackend()
  {
    this( JsRuntime.window() );
  }

  /**
   * Create backend for routing for specified window.
   *
   * @param window the window.
   */
  public HashBackend( @Nonnull final Object window )
  {
    _window = Js.uncheckedCast( Objects.requireNonNull( window ) );
  }

  @Nonnull
  @Override
  public String getLocation()
  {
    final String hash = _window.location().hash();
    return 0 == hash.length() ? "" : hash.substring( 1 );
  }

  @Override
  public void setLocation( @Nonnull final String hash )
  {
    final JsRuntime.Location location = _window.location();
    if ( 0 == hash.length() )
    {
      /*
       * The "hashchange" event handler is not invoked if only pushState is used to
       * change the url so we set it to empty before also using pushState.
       */
      location.setHash( "" );
      /*
       * This code is needed to remove the stray #.
       * See https://stackoverflow.com/questions/1397329/how-to-remove-the-hash-from-window-location-url-with-javascript-without-page-r/5298684#5298684
       */
      _window.history().replaceState( "", _window.document().title(), location.pathname() + location.search() );
    }
    else
    {
      location.setHash( hash );
    }
  }

  @Nonnull
  @Override
  public Object addListener( @Nonnull final LocationChangeListener handler )
  {
    final JsRuntime.EventListener eventListener = e -> handler.onLocationChange( getLocation() );
    _window.addEventListener( "hashchange", eventListener, false );
    return eventListener;
  }

  @Override
  public void removeListener( @Nonnull final Object handle )
  {
    _window.removeEventListener( "hashchange", (JsRuntime.EventListener) handle, false );
  }
}
