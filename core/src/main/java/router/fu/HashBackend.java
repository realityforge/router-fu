package router.fu;

import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import elemental2.dom.Window;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Hash based routing backend based on Elemental2.
 */
public class HashBackend
  implements Backend
{
  @Nonnull
  private final Window _window;

  /**
   * Create backend for routing for on current window.
   */
  public HashBackend()
  {
    this( DomGlobal.window );
  }

  /**
   * Create backend for routing for specified window.
   *
   * @param window the window.
   */
  public HashBackend( @Nonnull final Window window )
  {
    _window = Objects.requireNonNull( window );
  }

  @Nonnull
  @Override
  public String getLocation()
  {
    final String hash = _window.location.hash;
    return null == hash ? "" : hash.substring( 1 );
  }

  @Override
  public void setLocation( @Nonnull final String hash )
  {
    if ( 0 == hash.length() )
    {
      /*
       * "hashchanged" event handler is not invoked if only use pushState to
       * change the url so we set it to empty before also using pushState.
       */
      _window.location.hash = "";
      /*
       * This code is needed to remove the stray #.
       * See https://stackoverflow.com/questions/1397329/how-to-remove-the-hash-from-window-location-url-with-javascript-without-page-r/5298684#5298684
       */
      final String url = _window.location.pathname + _window.location.search;
      _window.history.replaceState( "", DomGlobal.document.title, url );
    }
    else
    {
      _window.location.hash = hash;
    }
  }

  @Nonnull
  @Override
  public Object addListener( @Nonnull final LocationChangeListener handler )
  {
    final EventListener eventListener = e -> handler.onLocationChange( getLocation() );
    _window.addEventListener( "hashchange", eventListener, false );
    return eventListener;
  }

  @Override
  public void removeListener( @Nonnull final Object handle )
  {
    _window.removeEventListener( "hashchange", (EventListener) handle, false );
  }
}
