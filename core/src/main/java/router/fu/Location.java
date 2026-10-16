package router.fu;

import java.util.Collections;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The result of routing a path.
 * The location contains the path as well as an ordered list of {@link RouteState} instances. The last
 * {@link RouteState} instance may be a terminal routing state and other states being non-terminal.
 */
public final class Location {

    private final String _path;

    private final List<RouteState> _states;

    public Location(final String path, final List<RouteState> states) {
        _path = path;
        _states = Collections.unmodifiableList(states);
    }

    /**
     * Return the path that generated this route.
     *
     * @return the associated path.
     */
    public String getPath() {
        return _path;
    }

    /**
     * Return the terminal state of routing if any.
     * This is essentially the state that the Route identified as terminal.
     * It will be the last one in the list if it exists.
     *
     * @return the terminal state or null if no such state.
     */
    public @Nullable RouteState getTerminalState() {
        if (_states.isEmpty()) {
            return null;
        } else {
            final RouteState state = _states.get(_states.size() - 1);
            return state.isTerminal() ? state : null;
        }
    }

    /**
     * Return the list of route states produced during routing.
     *
     * @return the list of route states produced during routing.
     */
    public List<RouteState> getStates() {
        return _states;
    }
}
