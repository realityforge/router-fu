package isolated;

import org.jspecify.annotations.NullMarked;
import router.fu.Parameter;

@NullMarked
public final class Browser {
    private Browser() {}

    public static String parameterName() {
        return new Parameter("id").getName();
    }
}
