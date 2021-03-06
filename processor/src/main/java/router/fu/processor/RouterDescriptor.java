package router.fu.processor;

import com.squareup.javapoet.ClassName;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.realityforge.proton.GeneratorUtil;
import org.realityforge.proton.ProcessorException;

final class RouterDescriptor
{
  @Nonnull
  private final PackageElement _packageElement;
  @Nonnull
  private final TypeElement _element;
  private boolean _arezComponent;
  private final LinkedHashMap<String, RouteDescriptor> _routes = new LinkedHashMap<>();
  private final LinkedHashMap<String, BoundParameterDescriptor> _boundParameters = new LinkedHashMap<>();
  private List<ExecutableElement> _routerRefMethods = Collections.emptyList();

  RouterDescriptor( @Nonnull final PackageElement packageElement,
                    @Nonnull final TypeElement element )
  {
    _packageElement = Objects.requireNonNull( packageElement );
    _element = Objects.requireNonNull( element );

    if ( ElementKind.CLASS != element.getKind() )
    {
      throw new ProcessorException( "@Router target must be a class", element );
    }
    else if ( element.getModifiers().contains( Modifier.ABSTRACT ) )
    {
      throw new ProcessorException( "@Router target must not be abstract", element );
    }
    else if ( element.getModifiers().contains( Modifier.FINAL ) )
    {
      throw new ProcessorException( "@Router target must not be final", element );
    }
    else if ( NestingKind.TOP_LEVEL != element.getNestingKind() &&
              !element.getModifiers().contains( Modifier.STATIC ) )
    {
      throw new ProcessorException( "@Router target must not be a non-static nested class", element );
    }

    final List<ExecutableElement> constructors = element.getEnclosedElements().stream().
      filter( m -> m.getKind() == ElementKind.CONSTRUCTOR ).
      map( m -> (ExecutableElement) m ).
      collect( Collectors.toList() );
    if ( !( 1 == constructors.size() &&
            constructors.get( 0 ).getParameters().isEmpty() &&
            !constructors.get( 0 ).getModifiers().contains( Modifier.PRIVATE ) ) )
    {
      throw new ProcessorException( "@Router target must have a single non-private, no-argument " +
                                    "constructor or the default constructor", element );
    }
  }

  boolean hasRouteNamed( @Nonnull final String name )
  {
    return _routes.containsKey( name );
  }

  @Nonnull
  RouteDescriptor getRouteByName( @Nonnull final String name )
  {
    final RouteDescriptor descriptor = _routes.get( name );
    assert null != descriptor;
    return descriptor;
  }

  void addRoute( @Nonnull final RouteDescriptor route )
  {
    assert !hasRouteNamed( route.getName() );
    _routes.put( route.getName(), route );
  }

  Collection<RouteDescriptor> getRoutes()
  {
    return _routes.values();
  }

  boolean hasBoundParameterNamed( @Nonnull final String name )
  {
    return _boundParameters.containsKey( name );
  }

  void addBoundParameter( @Nonnull final BoundParameterDescriptor boundParameter )
  {
    assert !hasRouteNamed( boundParameter.getName() );
    _boundParameters.put( boundParameter.getName(), boundParameter );
  }

  Collection<BoundParameterDescriptor> getBoundParameters()
  {
    return _boundParameters.values();
  }

  @Nonnull
  String getPackageName()
  {
    return _packageElement.getQualifiedName().toString();
  }

  @Nonnull
  ClassName getClassName()
  {
    return ClassName.get( getElement() );
  }

  @Nonnull
  TypeElement getElement()
  {
    return _element;
  }

  @Nonnull
  ClassName getServiceClassName()
  {
    return GeneratorUtil.getGeneratedClassName( _element, "", "Service" );
  }

  @Nonnull
  ClassName getRouterClassName()
  {
    return GeneratorUtil.getGeneratedClassName( _element, "RouterFu_", "" );
  }

  boolean isArezComponent()
  {
    return _arezComponent;
  }

  void setArezComponent( final boolean arezComponent )
  {
    _arezComponent = arezComponent;
  }

  void setRouterRefMethods( @Nonnull final List<ExecutableElement> routerRefMethods )
  {
    _routerRefMethods = Objects.requireNonNull( routerRefMethods );
  }

  @Nonnull
  List<ExecutableElement> getRouterRefMethods()
  {
    return _routerRefMethods;
  }
}
