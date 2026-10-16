package router.fu;

public class TestRegExpResult implements RegExMatchResult {
    private final String[] _resultGroups;

    TestRegExpResult(final String[] resultGroups) {
        _resultGroups = resultGroups;
    }

    @Override
    public int getGroupCount() {
        return _resultGroups.length;
    }

    @Override
    public String getGroup(final int i) {
        return _resultGroups[i];
    }
}
