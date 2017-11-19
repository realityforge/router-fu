package router.fu.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;

final class RouteDescriptor
{
  @Nonnull
  private final String _name;
  private final boolean _navigationTarget;
  private final boolean _partialMatch;
  private final ArrayList<Object> _parts = new ArrayList<>();
  private final LinkedHashMap<ParameterDescriptor, BoundParameterDescriptor> _boundParameters = new LinkedHashMap<>();
  private ExecutableElement _callback;

  RouteDescriptor( @Nonnull final String name, final boolean navigationTarget, final boolean partialMatch )
  {
    _name = Objects.requireNonNull( name );
    _navigationTarget = navigationTarget;
    _partialMatch = partialMatch;
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  boolean isNavigationTarget()
  {
    return _navigationTarget;
  }

  boolean isPartialMatch()
  {
    return _partialMatch;
  }

  void addParameter( @Nonnull final ParameterDescriptor parameter )
  {
    _parts.add( Objects.requireNonNull( parameter ) );
  }

  void addText( @Nonnull final String text )
  {
    _parts.add( Objects.requireNonNull( text ) );
  }

  @Nonnull
  ArrayList<Object> getParts()
  {
    return _parts;
  }

  @Nonnull
  List<ParameterDescriptor> getParameters()
  {
    return getParts().stream()
      .filter( ParameterDescriptor.class::isInstance )
      .map( ParameterDescriptor.class::cast ).
        collect( Collectors.toList() );
  }

  void bindParameter( @Nonnull final ParameterDescriptor parameter,
                      @Nonnull final BoundParameterDescriptor boundParameter )
  {
    assert getParameters().contains( parameter );
    _boundParameters.put( parameter, boundParameter );
  }

  @Nonnull
  LinkedHashMap<ParameterDescriptor, BoundParameterDescriptor> getBoundParameters()
  {
    return _boundParameters;
  }

  @Nullable
  ParameterDescriptor findParameterByName( @Nonnull final String parameterName )
  {
    return getParameters().stream().filter( p -> p.getName().equals( parameterName ) ).findAny().orElse( null );
  }

  boolean hasCallback()
  {
    return null != _callback;
  }

  @Nonnull
  ExecutableElement getCallback()
  {
    assert null != _callback;
    return _callback;
  }

  void setCallback( @Nonnull final ExecutableElement method )
  {
    assert null == _callback;
    _callback = Objects.requireNonNull( method );
  }
}
