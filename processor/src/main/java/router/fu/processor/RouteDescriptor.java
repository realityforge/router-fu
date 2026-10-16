package router.fu.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.ExecutableType;
import org.jspecify.annotations.Nullable;

final class RouteDescriptor {

    private final String _name;
    private final boolean _navigationTarget;
    private final boolean _partialMatch;

    private final List<Object> _parts = new ArrayList<>();

    private final Map<ParameterDescriptor, BoundParameterDescriptor> _boundParameters = new LinkedHashMap<>();
    private @Nullable ExecutableElement _callback;
    private @Nullable ExecutableType _callbackType;
    private int _locationIndex;
    private int _routeIndex;
    private int _parametersIndex;

    RouteDescriptor(final String name, final boolean navigationTarget, final boolean partialMatch) {
        _name = name;
        _navigationTarget = navigationTarget;
        _partialMatch = partialMatch;
    }

    String getName() {
        return _name;
    }

    boolean isNavigationTarget() {
        return _navigationTarget;
    }

    boolean isPartialMatch() {
        return _partialMatch;
    }

    void addParameter(final ParameterDescriptor parameter) {
        _parts.add(parameter);
    }

    void addText(final String text) {
        _parts.add(text);
    }

    List<Object> getParts() {
        return _parts;
    }

    List<ParameterDescriptor> getParameters() {
        return getParts().stream()
                .filter(ParameterDescriptor.class::isInstance)
                .map(ParameterDescriptor.class::cast)
                .collect(Collectors.toList());
    }

    void bindParameter(final ParameterDescriptor parameter, final BoundParameterDescriptor boundParameter) {
        assert getParameters().contains(parameter);
        _boundParameters.put(parameter, boundParameter);
    }

    Map<ParameterDescriptor, BoundParameterDescriptor> getBoundParameters() {
        return _boundParameters;
    }

    @Nullable
    ParameterDescriptor findParameterByName(final String parameterName) {
        return getParameters().stream()
                .filter(p -> p.getName().equals(parameterName))
                .findAny()
                .orElse(null);
    }

    boolean hasCallback() {
        return null != _callback;
    }

    ExecutableElement getCallback() {
        assert null != _callback;
        return _callback;
    }

    ExecutableType getCallbackType() {
        assert null != _callbackType;
        return _callbackType;
    }

    void setCallback(
            final ExecutableElement method,
            final ExecutableType methodType,
            final int locationIndex,
            final int routeIndex,
            final int parametersIndex) {
        assert null == _callback;
        _callback = method;
        _callbackType = methodType;
        _locationIndex = locationIndex;
        _routeIndex = routeIndex;
        _parametersIndex = parametersIndex;
    }

    int getLocationIndex() {
        return _locationIndex;
    }

    int getRouteIndex() {
        return _routeIndex;
    }

    int getParametersIndex() {
        return _parametersIndex;
    }
}
