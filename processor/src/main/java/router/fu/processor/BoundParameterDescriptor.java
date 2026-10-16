package router.fu.processor;

import java.util.LinkedHashMap;
import java.util.Map;

final class BoundParameterDescriptor {

    private final String _name;
    private final LinkedHashMap<RouteDescriptor, ParameterDescriptor> _bindings;

    BoundParameterDescriptor(final String name, final LinkedHashMap<RouteDescriptor, ParameterDescriptor> bindings) {
        _name = name;
        _bindings = bindings;
        for (final Map.Entry<RouteDescriptor, ParameterDescriptor> entry : _bindings.entrySet()) {
            entry.getKey().bindParameter(entry.getValue(), this);
        }
    }

    String getName() {
        return _name;
    }

    LinkedHashMap<RouteDescriptor, ParameterDescriptor> getBindings() {
        return _bindings;
    }
}
