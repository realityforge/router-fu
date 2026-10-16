package router.fu;

/**
 * Interface via which different routing backends can be implemented.
 */
public interface Backend {
    @FunctionalInterface
    interface LocationChangeListener {
        void onLocationChange(String location);
    }

    /**
     * Return the location.
     *
     * @return the location.
     */
    String getLocation();

    /**
     * Explicitly set the location.
     *
     * @param location the location.
     */
    void setLocation(String location);

    /**
     * Register a listener that is called back when location changes.
     *
     * @param handler the callback handler.
     * @return the handle that can be passed to {@link #removeListener(Object)} to remove listener.
     */
    Object addListener(LocationChangeListener handler);

    /**
     * De-register listener so it will not receive notifications when location changes.
     *
     * @param handle the handle returned by the {@link #addListener(LocationChangeListener)} method.
     */
    void removeListener(Object handle);
}
