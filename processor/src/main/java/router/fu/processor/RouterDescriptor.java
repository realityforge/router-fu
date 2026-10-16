package router.fu.processor;

import com.palantir.javapoet.ClassName;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.realityforge.proton.ElementsUtil;
import org.realityforge.proton.GeneratorUtil;
import org.realityforge.proton.MemberChecks;
import org.realityforge.proton.ProcessorException;

final class RouterDescriptor {

    private final PackageElement _packageElement;

    private final TypeElement _element;
    private boolean _arezComponent;
    private final LinkedHashMap<String, RouteDescriptor> _routes = new LinkedHashMap<>();
    private final LinkedHashMap<String, BoundParameterDescriptor> _boundParameters = new LinkedHashMap<>();
    private List<ExecutableElement> _routerRefMethods = Collections.emptyList();

    RouterDescriptor(final PackageElement packageElement, final TypeElement element) {
        _packageElement = packageElement;
        _element = element;

        MemberChecks.mustBeClass(Constants.ROUTER_ANNOTATION_CLASSNAME, element);
        MemberChecks.mustNotBeAbstract(Constants.ROUTER_ANNOTATION_CLASSNAME, element);
        MemberChecks.mustNotBeFinal(Constants.ROUTER_ANNOTATION_CLASSNAME, element);
        MemberChecks.mustNotBeNonStaticNestedType(Constants.ROUTER_ANNOTATION_CLASSNAME, element);

        final List<ExecutableElement> constructors = ElementsUtil.getConstructors(element);
        if (!(1 == constructors.size()
                && constructors.get(0).getParameters().isEmpty()
                && !constructors.get(0).getModifiers().contains(Modifier.PRIVATE))) {
            throw new ProcessorException(
                    "@Router target must have a single non-private, no-argument "
                            + "constructor or the default constructor",
                    element);
        }
    }

    boolean hasRouteNamed(final String name) {
        return _routes.containsKey(name);
    }

    RouteDescriptor getRouteByName(final String name) {
        final RouteDescriptor descriptor = _routes.get(name);
        assert null != descriptor;
        return descriptor;
    }

    void addRoute(final RouteDescriptor route) {
        assert !hasRouteNamed(route.getName());
        _routes.put(route.getName(), route);
    }

    Collection<RouteDescriptor> getRoutes() {
        return _routes.values();
    }

    boolean hasBoundParameterNamed(final String name) {
        return _boundParameters.containsKey(name);
    }

    void addBoundParameter(final BoundParameterDescriptor boundParameter) {
        assert !hasRouteNamed(boundParameter.getName());
        _boundParameters.put(boundParameter.getName(), boundParameter);
    }

    Collection<BoundParameterDescriptor> getBoundParameters() {
        return _boundParameters.values();
    }

    String getPackageName() {
        return _packageElement.getQualifiedName().toString();
    }

    ClassName getClassName() {
        return ClassName.get(getElement());
    }

    TypeElement getElement() {
        return _element;
    }

    ClassName getServiceClassName() {
        return GeneratorUtil.getGeneratedClassName(_element, "", "Service");
    }

    ClassName getRouterClassName() {
        return GeneratorUtil.getGeneratedClassName(_element, "RouterFu_", "");
    }

    boolean isArezComponent() {
        return _arezComponent;
    }

    void setArezComponent(final boolean arezComponent) {
        _arezComponent = arezComponent;
    }

    void setRouterRefMethods(final List<ExecutableElement> routerRefMethods) {
        _routerRefMethods = routerRefMethods;
    }

    List<ExecutableElement> getRouterRefMethods() {
        return _routerRefMethods;
    }
}
