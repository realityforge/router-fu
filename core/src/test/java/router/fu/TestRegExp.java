package router.fu;

import org.jspecify.annotations.Nullable;

public class TestRegExp implements RegExMatcher {
    private final @Nullable RegExMatchResult _resultGroups;

    TestRegExp(final String... resultGroups) {
        _resultGroups = new TestRegExpResult(resultGroups);
    }

    TestRegExp() {
        _resultGroups = null;
    }

    @Override
    public @Nullable RegExMatchResult match(final String text) {
        return _resultGroups;
    }
}
