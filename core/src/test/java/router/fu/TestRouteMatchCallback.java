package router.fu;

import java.util.Map;
import org.jspecify.annotations.Nullable;

class TestRouteMatchCallback implements RouteMatchCallback {
    private final MatchResult _result;

    TestRouteMatchCallback() {
        this(MatchResult.TERMINAL);
    }

    TestRouteMatchCallback(final MatchResult result) {
        _result = result;
    }

    @Override
    public MatchResult shouldMatch(
            final String location, final Route route, final Map<Parameter, @Nullable String> parameters) {
        return _result;
    }
}
