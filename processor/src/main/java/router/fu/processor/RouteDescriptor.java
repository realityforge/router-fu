package router.fu.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.ExecutableType;

final class RouteDescriptor
{
  @Nonnull
  private final String _name;
  private final boolean _navigationTarget;
  private final boolean _partialMatch;
  @Nonnull
  private final List<Object> _parts = new ArrayList<>();
  @Nonnull
  private final Map<ParameterDescriptor, BoundParameterDescriptor> _boundParameters = new LinkedHashMap<>();
  private ExecutableElement _callback;
  private ExecutableType _callbackType;
  private int _locationIndex;
  private int _routeIndex;
  private int _parametersIndex;

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
  List<Object> getParts()
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
  Map<ParameterDescriptor, BoundParameterDescriptor> getBoundParameters()
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

  @Nonnull
  ExecutableType getCallbackType()
  {
    assert null != _callbackType;
    return _callbackType;
  }

  void setCallback( @Nonnull final ExecutableElement method,
                    @Nonnull final ExecutableType methodType,
                    final int locationIndex,
                    final int routeIndex,
                    final int parametersIndex )
  {
    assert null == _callback;
    _callback = Objects.requireNonNull( method );
    _callbackType = Objects.requireNonNull( methodType );
    _locationIndex = locationIndex;
    _routeIndex = routeIndex;
    _parametersIndex = parametersIndex;
  }

  int getLocationIndex()
  {
    return _locationIndex;
  }

  int getRouteIndex()
  {
    return _routeIndex;
  }

  int getParametersIndex()
  {
    return _parametersIndex;
  }
}
