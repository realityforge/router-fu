package router.fu;

import org.jspecify.annotations.Nullable;

/**
 * Representation of a path parameter.
 */
public final class Parameter {

    private final String _name;
    private final @Nullable RegExMatcher _validator;

    public Parameter(final String name) {
        this(name, (RegExMatcher) null);
    }

    public Parameter(final String name, final @Nullable String validatorPattern) {
        this(name, null == validatorPattern ? null : new BrowserMatcher(validatorPattern));
    }

    public Parameter(final String name, final @Nullable Object validator) {
        this(name, null == validator ? null : new BrowserMatcher(validator));
    }

    Parameter(final String name, final @Nullable RegExMatcher validator) {
        _name = name;
        _validator = validator;
    }

    /**
     * Return the name of the parameter.
     *
     * @return the name of the parameter.
     */
    public String getName() {
        return _name;
    }

    /**
     * Return the validator for parameter if any.
     *
     * @return the validator for parameter if any.
     */
    public @Nullable RegExMatcher getValidator() {
        return _validator;
    }

    @Override
    public String toString() {
        return getName();
    }
}
