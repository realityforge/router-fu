package router.fu;

import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * The final check to see if the route matches.
 */
@FunctionalInterface
public interface RouteMatchCallback {
    /**
     * Callback invoked to determine the outcome of a match.
     * The route has had a preliminary match and the user code should decide if it is an actual match.
     *
     * @param location   the location that was preliminarily matched.
     * @param route      the route that was matched.
     * @param parameters the parameters extracted from location path.
     * @return the match result.
     */
    MatchResult shouldMatch(String location, Route route, Map<Parameter, @Nullable String> parameters);
}
