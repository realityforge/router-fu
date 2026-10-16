package router.fu;

import static org.realityforge.braincheck.Guards.*;

import org.jspecify.annotations.Nullable;
import org.realityforge.braincheck.BrainCheckConfig;

/**
 * This class represents the path segments used when reconstructing paths for route.
 */
public final class Segment {
    private final @Nullable Parameter _parameter;
    private final @Nullable String _path;

    /**
     * Create a path element for a static path component.
     *
     * @param path the static path component.
     */
    public Segment(final String path) {
        _parameter = null;
        _path = path;
    }

    /**
     * Create a path element for a parameter.
     *
     * @param parameter the parameter.
     */
    public Segment(final Parameter parameter) {
        _parameter = parameter;
        _path = null;
    }

    /**
     * Return the static path segment. This should not be called if {@link #isParameter()} returns true.
     *
     * @return the static path component.
     */
    public String getPath() {
        if (BrainCheckConfig.checkApiInvariants()) {
            apiInvariant(
                    () -> null != _path,
                    () -> "Segment.getPath() invoked on parameter path element with parameter named '" + _parameter
                            + "'");
        }
        assert null != _path;
        return _path;
    }

    /**
     * Return the parameter used to construct segment. This should only be called if {@link #isParameter()} returns true.
     *
     * @return the parameter component.
     */
    public Parameter getParameter() {
        if (BrainCheckConfig.checkApiInvariants()) {
            apiInvariant(
                    () -> null != _parameter,
                    () -> "Segment.getParameter() invoked on non-parameter path element with value '" + _path + "'");
        }
        assert null != _parameter;
        return _parameter;
    }

    /**
     * Return true if this path element is a parameter.
     *
     * @return true if this path element is a parameter.
     */
    public boolean isParameter() {
        return null != _parameter;
    }
}
