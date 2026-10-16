package router.fu.example;

import arez.Arez;
import java.util.Objects;
import java.util.Random;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import org.jspecify.annotations.Nullable;
import router.fu.RouteState;

public class Example {
    @JsMethod
    public static void start() {
        new Example().onModuleLoad();
    }

    private void onModuleLoad() {
        // Need to force AppState clinit to be run to ensure that it is run as top level transaction
        @SuppressWarnings("unused")
        final MyRouterService router = AppState.ROUTER;

        final Document document = document();
        Objects.requireNonNull(document.querySelector("#login"))
                .addEventListener("click", e -> Arez.context().safeAction(() -> AppState.AUTH.setUsername("Bob")));
        Objects.requireNonNull(document.querySelector("#logout"))
                .addEventListener("click", e -> Arez.context().safeAction(() -> AppState.AUTH.setUsername(null)));
        Objects.requireNonNull(document.querySelector("#route_regions"))
                .addEventListener("click", e -> AppState.ROUTER.gotoRegions());
        Objects.requireNonNull(document.querySelector("#route_region"))
                .addEventListener("click", e -> AppState.ROUTER.gotoRegion("ballarat"));
        Objects.requireNonNull(document.querySelector("#route_regionEvents"))
                .addEventListener("click", e -> AppState.ROUTER.gotoRegionEvents("ballarat"));
        Objects.requireNonNull(document.querySelector("#route_regionEvent"))
                .addEventListener("click", e -> AppState.ROUTER.gotoRegionEvent("ballarat", "42"));
        Objects.requireNonNull(document.querySelector("#randomize_regionCode"))
                .addEventListener(
                        "click", e -> AppState.ROUTER.updateRegionCode(String.valueOf(new Random().nextInt())));
        Objects.requireNonNull(document.querySelector("#randomize_eventId"))
                .addEventListener("click", e -> AppState.ROUTER.updateEventId(String.valueOf(new Random().nextInt())));
        Arez.context().observer(this::renderView);
    }

    private void renderView() {
        final Document document = document();
        final String username = AppState.AUTH.getUsername();
        Objects.requireNonNull(document.querySelector("#username")).setInnerHTML(null == username ? "" : username);
        Objects.requireNonNull(document.querySelector("#location"))
                .setInnerHTML(AppState.ROUTER.getLocation().getPath());
        final String regionCode = AppState.ROUTER.getRegionCode();
        Objects.requireNonNull(document.querySelector("#regionCode"))
                .setInnerHTML(null == regionCode ? "" : regionCode);
        final String eventId = AppState.ROUTER.getEventId();
        Objects.requireNonNull(document.querySelector("#eventId")).setInnerHTML(null == eventId ? "" : eventId);

        final RouteState state = AppState.ROUTER.getRegionFilterRouteState();
        if (null != state) {
            final String regionCode2 = state.getParameterValue(AppState.ROUTER.getRegionFilterRegionCodeParameter());
            log("RegionCode: " + regionCode2);
        } else {
            log("No RegionCode");
        }
    }

    @JsProperty(namespace = JsPackage.GLOBAL, name = "document")
    private static native Document document();

    @JsMethod(namespace = JsPackage.GLOBAL, name = "console.log")
    private static native void log(Object message);

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Document")
    private static final class Document {
        native @Nullable Element querySelector(String selector);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Element")
    private static final class Element {
        native void addEventListener(String type, EventListener listener);

        @JsProperty(name = "innerHTML")
        native void setInnerHTML(String value);
    }

    @FunctionalInterface
    @JsFunction
    private interface EventListener {
        void handleEvent(Object event);
    }
}
