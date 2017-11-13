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

public final class Route
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final PathElement[] _pathElements;
  @Nonnull
  private final PathParameter[] _pathParameters;
  @Nonnull
  private final RegExp _matcher;
  @Nonnull
  private final RouteMatchCallback _matchCallback;

  static String pathToPattern( @Nonnull final String path )
  {
    return "^" + path.replaceAll( "([\\-/\\\\\\^$\\*\\+\\?\\.\\(\\)\\|\\[\\]\\{\\}])", "\\\\$1" ) + "$";
  }

  public Route( @Nonnull final String name,
                @Nonnull final PathElement[] pathElements,
                @Nonnull final PathParameter[] pathParameters,
                @Nonnull final RegExp matcher,
                @Nonnull final RouteMatchCallback matchCallback )
  {
    _name = Objects.requireNonNull( name );
    _pathElements = Objects.requireNonNull( pathElements );
    _pathParameters = Objects.requireNonNull( pathParameters );
    _matcher = Objects.requireNonNull( matcher );
    _matchCallback = Objects.requireNonNull( matchCallback );
  }

  @Nonnull
  public String buildPath( @Nonnull final Map<String, String> parameters )
  {
    final HashSet<String> usedParameters = BrainCheckConfig.checkApiInvariants() ? new HashSet<>() : null;
    final StringBuilder sb = new StringBuilder();
    for ( final PathElement pathElement : _pathElements )
    {
      if ( pathElement.isParameter() )
      {
        final String parameterKey = pathElement.getValue();
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
        sb.append( parameterValue );
      }
      else
      {
        sb.append( pathElement.getValue() );
      }
    }
    if ( BrainCheckConfig.checkApiInvariants() )
    {
      assert null != usedParameters;
      final List<String> unusedParameters =
        parameters.keySet().stream().filter( k -> !usedParameters.contains( k ) ).collect( Collectors.toList() );
      apiInvariant( unusedParameters::isEmpty,
                    () -> "Route named '" + _name + "' expects all parameters to be used when building " +
                          "path but the following parameters are unused. Parameters: " + unusedParameters );
    }
    return sb.toString();
  }

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

  @Nonnull
  private PathParameter getParameterByIndex( final int index )
  {
    invariant( () -> _pathParameters.length > index,
               () -> "Route named '" + _name + "' expects a parameter at index " + index + " when matching " +
                     "location but no such parameter has been defined." );
    return _pathParameters[ index ];
  }
}
