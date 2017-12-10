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

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public String getLocation()
  {
    final String hash = _window.location.getHash();
    return null == hash ? "" : hash.substring( 1 );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLocation( @Nonnull final String hash )
  {
    final String value = hash.isEmpty() ? null : "#" + hash;
    _window.location.setHash( value );
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public Object addListener( @Nonnull final LocationChangeListener handler )
  {
    final EventListener eventListener = e -> handler.onLocationChange( getLocation() );
    _window.addEventListener( "hashchange", eventListener, false );
    return eventListener;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener( @Nonnull final Object handle )
  {
    _window.removeEventListener( "hashchange", (EventListener) handle, false );
  }
}
