package router.fu;

import elemental2.core.RegExp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.realityforge.braincheck.BrainCheckConfig;
import static org.realityforge.braincheck.Guards.*;

/**
 * A named pattern that can be matched during routing.
 * Matching the pattern can produce parameters as described by the {@link #_pathParameters} field. If the
 * toolkit identifies this route as matching and all patterns pass validation, then the toolkit invokes the
 * {@link #_matchCallback} callback to complete the match process.
 *
 * <p>Some routes can also be used as targets of navigation. These routes are constructed with {@link PathElement}
 * instances passed to them and will return true from {@link #isNavigationTarget()}. The {@link #buildPath(Map)}
 * method can be invoked on navigation targets.</p>
 */
public final class Route
{
  /**
   * The name of route. An opaque string primarily useful for users.
   */
  @Nonnull
  private final String _name;
  /**
   * The list of elements used for constructing a url if route can be a navigation target. Otherwise this is null.
   */
  @Nullable
  private final PathElement[] _pathElements;
  /**
   * Descriptors for parameters extracted from the path.
   */
  @Nonnull
  private final PathParameter[] _pathParameters;
  /**
   * The regular expression that matches the path and extracts parameters.
   */
  @Nonnull
  private final RegExp _matcher;
  /**
   * The callback that makes the final decision whether a route matches.
   */
  @Nonnull
  private final RouteMatchCallback _matchCallback;

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public Route( @Nonnull final String name,
                @Nullable final PathElement[] pathElements,
                @Nonnull final PathParameter[] pathParameters,
                @Nonnull final RegExp matcher,
                @Nonnull final RouteMatchCallback matchCallback )
  {
    _name = Objects.requireNonNull( name );
    _pathElements = pathElements;
    _pathParameters = Objects.requireNonNull( pathParameters );
    _matcher = Objects.requireNonNull( matcher );
    _matchCallback = Objects.requireNonNull( matchCallback );
  }

  public boolean isNavigationTarget()
  {
    return null != _pathElements;
  }

  @Nonnull
  public String buildPath( @Nonnull final Map<String, String> parameters )
  {
    apiInvariant( this::isNavigationTarget,
                  () -> "Route named '" + _name + "' can not have buildPath() invoked on it as is not a target." );
    assert null != _pathElements;
    final HashSet<String> usedParameters = BrainCheckConfig.checkApiInvariants() ? new HashSet<>() : null;
    final StringBuilder sb = new StringBuilder();
    for ( final PathElement pathElement : _pathElements )
    {
      if ( pathElement.isParameter() )
      {
        final String parameterKey = pathElement.getPath();
        apiInvariant( () -> parameters.containsKey( parameterKey ),
                      () -> "Route named '" + _name + "' expects a parameter named '" + parameterKey + "' to be " +
                            "supplied when building path but no such parameter was supplied. " +
                            "Parameters: " + parameters );
        if ( BrainCheckConfig.checkApiInvariants() )
        {
          assert null != usedParameters;
          usedParameters.add( parameterKey );
        }
        final String parameterValue = parameters.get( parameterKey );
        apiInvariant( () -> null == pathElement.getParameter().getValidator() ||
                            pathElement.getParameter().getValidator().test( parameterValue ),
                      () -> "Route named '" + _name + "' has a parameter named '" + parameterKey + "' and " +
                            "a value '" + parameterValue + "' has been passed that does not pass validation check." );
        sb.append( parameterValue );
      }
      else
      {
        sb.append( pathElement.getPath() );
      }
    }
    final String location = sb.toString();
    if ( BrainCheckConfig.checkApiInvariants() )
    {
      assert null != usedParameters;
      final List<String> unusedParameters =
        parameters.keySet().stream().filter( k -> !usedParameters.contains( k ) ).collect( Collectors.toList() );
      apiInvariant( unusedParameters::isEmpty,
                    () -> "Route named '" + _name + "' expects all parameters to be used when building " +
                          "path but the following parameters are unused. Parameters: " + unusedParameters );
      final RouteState routeState = match( location );
      invariant( () -> null != routeState && Objects.equals( routeState.getParameters(), parameters ),
                 () -> "Route named '" + _name + "' had buildPath() invoked with parameters " + parameters +
                       " produced path '" + location + "' and if this is matched against the same route it produces " +
                       "different parameters: " + ( null != routeState ? routeState.getParameters() : null ) );
    }
    return location;
  }

  /**
   * Attempt to match the specified location.
   *
   * @return the reoute state if a match is successful, null otherwise.
   */
  @Nullable
  public RouteState match( @Nonnull final String location )
  {
    Objects.requireNonNull( location );
    final String[] groups = _matcher.exec( location );
    if ( null != groups )
    {
      final HashMap<String, String> matchData = new HashMap<>();
      //Group 0 is the whole string so we can skip it
      for ( int i = 1; i < groups.length; i++ )
      {
        final String value = groups[ i ];
        final int paramIndex = i - 1;
        final PathParameter parameter = getParameterByIndex( paramIndex );

        matchData.put( parameter.getName(), value );
      }
      final MatchResult matchResult = _matchCallback.shouldMatch( location, this, matchData );
      if ( MatchResult.NO_MATCH != matchResult )
      {
        return new RouteState( this, matchData, MatchResult.TERMINAL == matchResult );
      }
      else
      {
        return null;
      }
    }
    else
    {
      return null;
    }
  }

  /**
   * Return the parameter descriptor at index.
   *
   * @return the parameter descriptor at index.
   */
  @Nonnull
  PathParameter getParameterByIndex( final int index )
  {
    invariant( () -> _pathParameters.length > index,
               () -> "Route named '" + _name + "' expects a parameter at index " + index + " when matching " +
                     "location but no such parameter has been defined." );
    return _pathParameters[ index ];
  }
}
