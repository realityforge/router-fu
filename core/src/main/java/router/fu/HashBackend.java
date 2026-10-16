package router.fu;

import java.util.Objects;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import org.jspecify.annotations.Nullable;

/**
 * Hash based routing backend.
 */
public class HashBackend implements Backend {

    private final Window _window;

    /**
     * Create backend for routing for on current window.
     */
    public HashBackend() {
        this(window());
    }

    /**
     * Create backend for routing for specified window.
     *
     * @param window the window.
     */
    public HashBackend(final Object window) {
        _window = Objects.requireNonNull(Js.<@Nullable Window>uncheckedCast(window));
    }

    @Override
    public String getLocation() {
        final String hash = _window.location().hash();
        return 0 == hash.length() ? "" : hash.substring(1);
    }

    @Override
    public void setLocation(final String hash) {
        final Location location = _window.location();
        if (0 == hash.length()) {
            /*
             * The "hashchange" event handler is not invoked if only pushState is used to
             * change the url so we set it to empty before also using pushState.
             */
            location.setHash("");
            /*
             * This code is needed to remove the stray #.
             * See https://stackoverflow.com/questions/1397329/how-to-remove-the-hash-from-window-location-url-with-javascript-without-page-r/5298684#5298684
             */
            _window.history().replaceState("", _window.document().title(), location.pathname() + location.search());
        } else {
            location.setHash(hash);
        }
    }

    @Override
    public Object addListener(final LocationChangeListener handler) {
        final EventListener eventListener = e -> handler.onLocationChange(getLocation());
        _window.addEventListener("hashchange", eventListener, false);
        return eventListener;
    }

    @Override
    public void removeListener(final Object handle) {
        _window.removeEventListener("hashchange", (EventListener) handle, false);
    }

    @JsProperty(namespace = JsPackage.GLOBAL, name = "window")
    private static native Window window();

    @FunctionalInterface
    @JsFunction
    private interface EventListener {
        void handleEvent(Object event);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Document")
    private static final class Document {
        private Document() {}

        @JsProperty(name = "title")
        native String title();
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "History")
    private static final class History {
        private History() {}

        native void replaceState(Object data, String unused, String url);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Location")
    private static final class Location {
        private Location() {}

        @JsProperty(name = "hash")
        native String hash();

        @JsProperty(name = "hash")
        native void setHash(String hash);

        @JsProperty(name = "pathname")
        native String pathname();

        @JsProperty(name = "search")
        native String search();
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Window")
    private static final class Window {
        private Window() {}

        native void addEventListener(String type, EventListener listener, boolean useCapture);

        @JsProperty(name = "document")
        native Document document();

        @JsProperty(name = "history")
        native History history();

        @JsProperty(name = "location")
        native Location location();

        native void removeEventListener(String type, EventListener listener, boolean useCapture);
    }
}
