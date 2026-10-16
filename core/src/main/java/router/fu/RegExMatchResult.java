package router.fu;

import org.jspecify.annotations.Nullable;

interface RegExMatchResult {
    int getGroupCount();

    @Nullable
    String getGroup(int i);
}
