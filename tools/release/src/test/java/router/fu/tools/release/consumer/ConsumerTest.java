package router.fu.tools.release.consumer;

import static org.junit.Assert.*;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import router.fu.Backend;

@NullMarked
@RunWith(JUnit4.class)
public final class ConsumerTest {
    @Test
    public void generatedRouterUsesPublishedRuntime() {
        final var router = new RouterFu_Catalog(new MemoryBackend("/items/42"));
        assertEquals("42", router.getId());
        router.updateId("7");
        assertEquals("/items/7", router.getLocation().getPath());
        assertEquals("7", router.getId());
    }

    @Test
    public void emptyRouterKeepsUnmatchedLocation() {
        final var router = new RouterFu_EmptyRouter(new MemoryBackend("/unmatched"));
        assertEquals("/unmatched", router.getLocation().getPath());
        assertTrue(router.getLocation().getStates().isEmpty());
        router.reRoute();
    }

    private static final class MemoryBackend implements Backend {
        private String location;
        private @Nullable LocationChangeListener listener;

        private MemoryBackend(String location) {
            this.location = location;
        }

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
}
