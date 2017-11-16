package router.fu.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

final class RouteDescriptor
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _path;
  private final boolean _navigationTarget;
  private final boolean _partialMatch;
  private final ArrayList<Object> _parts = new ArrayList<>();

  RouteDescriptor( @Nonnull final String name,
                   @Nonnull final String path,
                   final boolean navigationTarget,
                   final boolean partialMatch )
  {
    _name = Objects.requireNonNull( name );
    _path = Objects.requireNonNull( path );
    _navigationTarget = navigationTarget;
    _partialMatch = partialMatch;
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  @Nonnull
  String getPascalCaseName()
  {
    if ( Character.isUpperCase( _name.charAt( 0 ) ) )
    {
      return _name;
    }
    else
    {
      return Character.toUpperCase( _name.charAt( 0 ) ) + (_name.length() > 1 ? _name.substring( 1 ) : "");
    }
  }

  @Nonnull
  String getPath()
  {
    return _path;
  }

  boolean isNavigationTarget()
  {
    return _navigationTarget;
  }

  boolean isPartialMatch()
  {
    return _partialMatch;
  }

  void addParameter( @Nonnull final ParameterDescriptor parameter )
  {
    _parts.add( Objects.requireNonNull( parameter ) );
  }

  void addText( @Nonnull final String text )
  {
    _parts.add( Objects.requireNonNull( text ) );
  }

  @Nonnull
  ArrayList<Object> getParts()
  {
    return _parts;
  }

  @Nonnull
  List<ParameterDescriptor> getParameters()
  {
    return getParts().stream()
      .filter( ParameterDescriptor.class::isInstance )
      .map( ParameterDescriptor.class::cast ).
        collect( Collectors.toList() );
  }
}
