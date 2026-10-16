package isolated;

import org.jspecify.annotations.NullMarked;
import router.fu.Backend;

@NullMarked
public final class Consumer {
    private Consumer() {}

    public static void main(String[] args) throws Exception {
        final var router = new RouterFu_EmptyRouter(new MemoryBackend());
        if (!router.getLocation().getPath().equals("/unmatched")
                || !router.getLocation().getStates().isEmpty()) {
            throw new AssertionError("Packaged processor and runtime must route the initial backend location");
        }
        for (final String resource :
                java.util.List.of("org/realityforge/braincheck/Guards.class", "jsinterop/base/Js.class")) {
            final var urls =
                    java.util.Collections.list(Consumer.class.getClassLoader().getResources(resource));
            if (urls.size() != 1)
                throw new AssertionError("Expected one shared runtime definition: " + resource + " " + urls);
        }
        if (!Browser.parameterName().equals("id")) throw new AssertionError("Packaged runtime API");
        router.reRoute();
        Class.forName("isolated.RouterFu_Catalog");
    }

    private static final class MemoryBackend implements Backend {
        @Override
        public String getLocation() {
            return "/unmatched";
        }

        @Override
        public void setLocation(String location) {}

        @Override
        public Object addListener(LocationChangeListener handler) {
            return handler;
        }

        @Override
        public void removeListener(Object handle) {}
    }
}
