package router.fu;

import java.util.Map;
import javax.annotation.Nonnull;

class TestRouteMatchCallback
  implements RouteMatchCallback
{
  @Nonnull
  @Override
  public MatchResult shouldMatch( @Nonnull final String location,
                                  @Nonnull final Route route,
                                  @Nonnull final Map<String, String> parameters )
  {
    return MatchResult.TERMINAL;
  }
}
