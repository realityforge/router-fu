package router.fu;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class SegmentTest extends AbstractRouterFuTest {
    @Test
    public void staticPath() {
        final String path = "/path";
        final var segment = new Segment(path);

        assertFalse(segment.isParameter());
        assertEquals(path, segment.getPath());

        final IllegalStateException exception = assertThrows(IllegalStateException.class, segment::getParameter);
        assertEquals(
                "Segment.getParameter() invoked on non-parameter path element with value '" + path + "'",
                exception.getMessage());
    }

    @Test
    public void parameterPath() {
        final String parameterName = "id";
        final var parameter = new Parameter(parameterName);
        final var segment = new Segment(parameter);

        assertTrue(segment.isParameter());
        assertEquals(parameter, segment.getParameter());

        final IllegalStateException exception = assertThrows(IllegalStateException.class, segment::getPath);
        assertEquals(
                "Segment.getPath() invoked on parameter path element with parameter named '" + parameterName + "'",
                exception.getMessage());
    }
}
