package router.fu;

interface RegExMatchResult {
    int getGroupCount();

    @org.jspecify.annotations.Nullable
    String getGroup(int i);
}
