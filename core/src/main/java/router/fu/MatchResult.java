package router.fu;

/**
 * Enum that determines how to proceed after a basic path match.
 */
public enum MatchResult
{
  /**
   * No match. Skip route and continue matching.
   */
  NO_MATCH,
  /**
   * Route is matched but continue to collect other matching routes.
   */
  NON_TERMINAL,
  /**
   * Route is matched and stop looking for other routes to match.
   */
  TERMINAL
}
