package router.fu;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class RouteState
{
  private final Route _route;
  private final Map<String, String> _parameters;
  private final boolean _terminal;

  public RouteState( @Nonnull final Route route, @Nonnull final Map<String, String> parameters, final boolean terminal )
  {
    _route = Objects.requireNonNull( route );
    _parameters = Objects.requireNonNull( parameters );
    _terminal = terminal;
  }

  @Nonnull
  public Route getRoute()
  {
    return _route;
  }

  @Nonnull
  public Map<String, String> getParameters()
  {
    return _parameters;
  }

  public boolean isTerminal()
  {
    return _terminal;
  }
}
