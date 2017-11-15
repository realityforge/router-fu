package router.fu.processor;

import com.squareup.javapoet.ClassName;
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
import javax.lang.model.type.DeclaredType;

final class RouterDescriptor
{
  @Nonnull
  private final PackageElement _packageElement;
  @Nonnull
  private final TypeElement _element;
  private boolean _arezComponent;

  RouterDescriptor( @Nonnull final PackageElement packageElement,
                    @Nonnull final TypeElement element )
  {
    _packageElement = Objects.requireNonNull( packageElement );
    _element = Objects.requireNonNull( element );

    if ( ElementKind.CLASS != element.getKind() )
    {
      throw new RouterProcessorException( "@Router target must be a class", element );
    }
    else if ( element.getModifiers().contains( Modifier.ABSTRACT ) )
    {
      throw new RouterProcessorException( "@Router target must not be abstract", element );
    }
    else if ( element.getModifiers().contains( Modifier.FINAL ) )
    {
      throw new RouterProcessorException( "@Router target must not be final", element );
    }
    else if ( NestingKind.TOP_LEVEL != element.getNestingKind() &&
              !element.getModifiers().contains( Modifier.STATIC ) )
    {
      throw new RouterProcessorException( "@Router target must not be a non-static nested class", element );
    }

    final List<ExecutableElement> constructors = element.getEnclosedElements().stream().
      filter( m -> m.getKind() == ElementKind.CONSTRUCTOR ).
      map( m -> (ExecutableElement) m ).
      collect( Collectors.toList() );
    if ( !( 1 == constructors.size() &&
            constructors.get( 0 ).getParameters().isEmpty() &&
            !constructors.get( 0 ).getModifiers().contains( Modifier.PRIVATE ) ) )
    {
      throw new RouterProcessorException( "@Router target must have a single non-private, no-argument " +
                                          "constructor or the default constructor", element );
    }
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
  DeclaredType getDeclaredType()
  {
    return (DeclaredType) _element.asType();
  }

  @Nonnull
  String getServiceName()
  {
    return _element.getSimpleName() + "Service";
  }

  @Nonnull
  ClassName getServiceClassName()
  {
    return toClassName( getServiceName() );
  }

  @Nonnull
  String getRouterName()
  {
    return "RouterFu_" + _element.getSimpleName();
  }

  @Nonnull
  ClassName getRouterClassName()
  {
    return toClassName( getRouterName() );
  }

  @Nonnull
  private ClassName toClassName( @Nonnull final String cname )
  {
    return ClassName.get( getPackageName(), getNestedClassPrefix() + cname );
  }

  @Nonnull
  String getNestedClassPrefix()
  {
    final StringBuilder name = new StringBuilder();
    TypeElement t = getElement();
    while ( NestingKind.TOP_LEVEL != t.getNestingKind() )
    {
      t = (TypeElement) t.getEnclosingElement();
      name.insert( 0, t.getSimpleName() + "$" );
    }
    return name.toString();
  }

  @Nonnull
  ClassName getRouterClassNameToConstruct()
  {
    return toClassName( ( isArezComponent() ? "Arez_" : "" ) + getRouterName() );
  }

  boolean isArezComponent()
  {
    return _arezComponent;
  }

  void setArezComponent( final boolean arezComponent )
  {
    _arezComponent = arezComponent;
  }
}