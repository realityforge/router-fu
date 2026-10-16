package router.fu;

import java.util.Map;
import org.jspecify.annotations.Nullable;

public final class RouteState {
    private final Route _route;
    private final Map<Parameter, @Nullable String> _parameters;
    private final boolean _terminal;

    public RouteState(final Route route, final Map<Parameter, @Nullable String> parameters, final boolean terminal) {
        _route = route;
        _parameters = parameters;
        _terminal = terminal;
    }

    public Route getRoute() {
        return _route;
    }

    public Map<Parameter, @Nullable String> getParameters() {
        return _parameters;
    }

    /**
     * Return a non-null capture for the parameter.
     * This requires a captured value; use {@link #getParameters()} to inspect unmatched native groups.
     */
    public String getParameterValue(final Parameter parameter) {
        final String value = _parameters.get(parameter);
        assert null != value;
        return value;
    }

    public boolean isTerminal() {
        return _terminal;
    }
}
