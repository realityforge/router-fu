package router.fu.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

final class BoundParameterDescriptor
{
  @Nonnull
  private final String _name;
  private Map<RouteDescriptor, ParameterDescriptor> _bindings = new HashMap<>();

  BoundParameterDescriptor( @Nonnull final String name,
                            @Nonnull final Map<RouteDescriptor, ParameterDescriptor> bindings )
  {
    _name = Objects.requireNonNull( name );
    _bindings = Objects.requireNonNull( bindings );
    for ( final Map.Entry<RouteDescriptor, ParameterDescriptor> entry : _bindings.entrySet() )
    {
      entry.getKey().bindParameter( entry.getValue(), this );
    }
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  @Nonnull
  Map<RouteDescriptor, ParameterDescriptor> getBindings()
  {
    return _bindings;
  }
}
