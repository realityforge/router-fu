package router.fu;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

class TestRouteMatchCallback
  implements RouteMatchCallback
{
  private final MatchResult _result;

  TestRouteMatchCallback()
  {
    this( MatchResult.TERMINAL );
  }

  TestRouteMatchCallback( @Nonnull final MatchResult result )
  {
    _result = Objects.requireNonNull( result );
  }

  @Nonnull
  @Override
  public MatchResult shouldMatch( @Nonnull final String location,
                                  @Nonnull final Route route,
                                  @Nonnull final Map<String, String> parameters )
  {
    return _result;
  }
}
