package router.fu;

import org.jspecify.annotations.Nullable;

interface RegExMatcher {
    @Nullable
    RegExMatchResult match(String text);
}
