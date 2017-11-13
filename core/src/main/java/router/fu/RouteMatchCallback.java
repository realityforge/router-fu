package router.fu;

import java.util.Map;
import javax.annotation.Nonnull;

/**
 * The final check to see if the route matches.
 */
@FunctionalInterface
public interface RouteMatchCallback
{
  @Nonnull
  MatchResult shouldMatch( @Nonnull String location,
                           @Nonnull Route route,
                           @Nonnull Map<String, String> parameters );
}
