package router.fu;

import java.util.Objects;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsArrayLike;
import org.jspecify.annotations.Nullable;

final class BrowserMatcher implements RegExMatcher {

    private final RegExp _matcher;

    BrowserMatcher(final String pattern) {
        _matcher = new RegExp(pattern);
    }

    BrowserMatcher(final Object matcher) {
        _matcher = Objects.requireNonNull(Js.<@Nullable RegExp>uncheckedCast(matcher));
    }

    @Override
    public @Nullable RegExMatchResult match(final String text) {
        final JsArrayLike<@Nullable String> result = _matcher.exec(text);
        return null == result ? null : new BrowserMatchResult(result);
    }

    @JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "RegExp")
    private static final class RegExp {
        RegExp(String pattern) {}

        native @Nullable JsArrayLike<@Nullable String> exec(String text);
    }

    private static final class BrowserMatchResult implements RegExMatchResult {

        private final JsArrayLike<@Nullable String> _groups;

        private BrowserMatchResult(final JsArrayLike<@Nullable String> groups) {
            _groups = groups;
        }

        @Override
        public int getGroupCount() {
            return _groups.getLength();
        }

        @Override
        public @Nullable String getGroup(final int i) {
            return _groups.getAt(i);
        }
    }
}
