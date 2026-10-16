package router.fu;

import static org.junit.Assert.*;

import org.jspecify.annotations.Nullable;

final class TestBackend implements Backend {
    private @Nullable LocationChangeListener _handler;

    private String _location = "";

    @Override
    public String getLocation() {
        return _location;
    }

    @Override
    public void setLocation(final String location) {
        _location = location;
    }

    @Override
    public Object addListener(final LocationChangeListener handler) {
        _handler = handler;
        return handler;
    }

    @Override
    public void removeListener(final Object handle) {
        if (null != _handler) {
            assertEquals(handle, _handler);
            _handler = null;
        }
    }

    @Nullable
    LocationChangeListener getHandler() {
        return _handler;
    }
}
