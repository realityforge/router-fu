package router.fu;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Result of performing a routing on location.
 * The result is an ordered list of {@link RouteState} instances with the last instance
 * potentially being a terminal routing state and other states being non-terminal.
 */
public final class RouteLocation
{
  @Nonnull
  private final String _path;
  @Nonnull
  private final List<RouteState> _states;

  public RouteLocation( @Nonnull final String path, @Nonnull final List<RouteState> states )
  {
    _path = Objects.requireNonNull( path );
    _states = Collections.unmodifiableList( Objects.requireNonNull( states ) );
  }

  /**
   * Return the path that generated this route.
   *
   * @return the associated path.
   */
  @Nonnull
  public String getPath()
  {
    return _path;
  }

  /**
   * Return the terminal state of routing if any.
   * This is essentially the state that the Route identified as terminal.
   * It will be the last one in the list if it exists.
   *
   * @return the terminal state or null if no such state.
   */
  @Nullable
  public RouteState getTerminalState()
  {
    if ( _states.isEmpty() )
    {
      return null;
    }
    else
    {
      final RouteState state = _states.get( _states.size() - 1 );
      return state.isTerminal() ? state : null;
    }
  }

  /**
   * Return the list of route states produced during routing.
   *
   * @return the list of route states produced during routing.
   */
  @Nonnull
  public List<RouteState> getStates()
  {
    return _states;
  }
}
