package router.fu;

/**
 * The listener that is notified on routing changes.
 */
@FunctionalInterface
public interface LocationListener {
    /**
     * Invoked when the location has been updated.
     *
     * @param location the new location.
     */
    void onLocationChanged(Location location);
}
