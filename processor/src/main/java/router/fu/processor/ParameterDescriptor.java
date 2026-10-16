package router.fu.processor;

import org.jspecify.annotations.Nullable;

record ParameterDescriptor(String name, @Nullable String constraint, boolean optional) {
    String getKey() {
        return name + (null == constraint ? "" : "<" + constraint + ">");
    }

    String getFieldName() {
        return name + (null == constraint ? "" : "_" + Math.abs(constraint.hashCode()));
    }
}
