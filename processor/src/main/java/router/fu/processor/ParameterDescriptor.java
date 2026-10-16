package router.fu.processor;

import org.jspecify.annotations.Nullable;

final class ParameterDescriptor {

    private final String _name;
    private final @Nullable String _constraint;
    private final boolean _optional;

    ParameterDescriptor(final String name, final @Nullable String constraint, final boolean optional) {
        _name = name;
        _constraint = constraint;
        _optional = optional;
    }

    String getKey() {
        return getName() + (null == _constraint ? "" : "<" + _constraint + ">");
    }

    String getFieldName() {
        return getName() + (null == _constraint ? "" : "_" + Math.abs(_constraint.hashCode()));
    }

    String getName() {
        return _name;
    }

    @Nullable
    String getConstraint() {
        return _constraint;
    }

    boolean isOptional() {
        return _optional;
    }
}
