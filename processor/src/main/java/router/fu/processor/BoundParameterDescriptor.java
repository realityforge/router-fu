package router.fu.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

final class BoundParameterDescriptor
{
  @Nonnull
  private final String _name;
  private final LinkedHashMap<RouteDescriptor, ParameterDescriptor> _bindings;

  BoundParameterDescriptor( @Nonnull final String name,
                            @Nonnull final LinkedHashMap<RouteDescriptor, ParameterDescriptor> bindings )
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
  LinkedHashMap<RouteDescriptor, ParameterDescriptor> getBindings()
  {
    return _bindings;
  }
}
