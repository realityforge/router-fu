package router.fu.browser;

import java.util.concurrent.atomic.AtomicInteger;
import jsinterop.annotations.JsMethod;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.realityforge.braincheck.BrainCheckConfig;
import router.fu.Backend;
import router.fu.HashBackend;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;

@NullMarked
public final class BrowserProbe {
    private static final HashBackend BACKEND = new HashBackend();
    private static final AtomicInteger CHANGES = new AtomicInteger();
    private static Object handle = new Object();

    private BrowserProbe() {}

    @JsMethod
    public static void start() {
        final var first = new Parameter("first");
        final var second = new Parameter("second");
        final var route = new Route(
                "native",
                null,
                new Parameter[] {first, second},
                "^(a)?(b)$",
                (location, matched, parameters) -> MatchResult.TERMINAL);
        final var state = route.match("b");
        if (state == null
                || !state.getParameters().containsKey(first)
                || state.getParameters().get(first) != null
                || !"b".equals(state.getParameterValue(second))
                || route.match("c") != null) {
            throw new AssertionError("Native regexp capture contract");
        }
        final var validated = new Route(
                "validated",
                null,
                new Parameter[] {new Parameter("id", "^[0-9]+$")},
                "^(.*)$",
                (location, matched, parameters) -> MatchResult.TERMINAL);
        if (validated.match("42") == null || validated.match("value") != null) {
            throw new AssertionError("Native regexp validation");
        }
        generatedContracts();
        BACKEND.setLocation("");
        if (!BACKEND.getLocation().isEmpty()) {
            throw new AssertionError("Empty hash location");
        }
        CHANGES.set(0);
        handle = BACKEND.addListener(location -> CHANGES.incrementAndGet());
    }

    @JsMethod
    public static boolean checksEnabled() {
        return BrainCheckConfig.checkApiInvariants();
    }

    private static void generatedContracts() {
        final var backend = new MemoryBackend();
        final var router = new RouterFu_BrowserRouter(backend);
        if (router.getRegionRouteState() == null || !"".equals(router.getId())) {
            throw new AssertionError("Optional generated parameter must capture an empty string");
        }
        router.updateId("42");
        if (!"/regions/42".equals(router.getLocation().getPath()) || !"42".equals(router.getId())) {
            throw new AssertionError("Bound parameter update");
        }
        backend.setLocation("/unmatched");
        if (router.getRegionRouteState() != null || router.getId() != null) {
            throw new AssertionError("Unmatched route must clear generated state");
        }
    }

    private static final class MemoryBackend implements Backend {
        private String location = "/regions/";
        private @Nullable LocationChangeListener listener;

        @Override
        public String getLocation() {
            return location;
        }

        @Override
        public void setLocation(String value) {
            location = value;
            if (listener != null) {
                listener.onLocationChange(value);
            }
        }

        @Override
        public Object addListener(LocationChangeListener value) {
            listener = value;
            return value;
        }

        @Override
        public void removeListener(Object handle) {
            listener = null;
        }
    }

    @JsMethod
    public static void change(String value) {
        BACKEND.setLocation(value);
    }

    @JsMethod
    public static int changes() {
        return CHANGES.get();
    }

    @JsMethod
    public static String location() {
        return BACKEND.getLocation();
    }

    @JsMethod
    public static void stop() {
        BACKEND.removeListener(handle);
    }
}
