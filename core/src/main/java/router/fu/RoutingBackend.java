package router.fu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface via which different routing backends can be implemented.
 */
public interface RoutingBackend
{
  @FunctionalInterface
  interface LocationChangeListener
  {
    void onLocationChange( @Nullable String location );
  }

  /**
   * Return the location.
   *
   * @return the location.
   */
  @Nullable
  String getLocation();

  /**
   * Explicitly set the location.
   *
   * @param location the location.
   */
  void setLocation( @Nullable String location );

  /**
   * Register a listener that is called back when location changes.
   *
   * @param handler the callback handler.
   * @return the handle that can be passed to {@link #removeListener(Object)} to remove listener.
   */
  @Nonnull
  Object addListener( @Nonnull LocationChangeListener handler );

  /**
   * De-register listener so it will not receive notifications when location changes.
   *
   * @param handle the handle returned by the {@link #addListener(LocationChangeListener)} method.
   */
  void removeListener( @Nonnull Object handle );
}
