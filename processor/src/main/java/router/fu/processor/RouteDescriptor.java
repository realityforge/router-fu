package router.fu.processor;

import java.util.Objects;
import javax.annotation.Nonnull;

final class RouteDescriptor
{
  @Nonnull
  private final String _name;
  @Nonnull
  private final String _path;
  private final boolean _navigationTarget;
  private final boolean _partialMatch;

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
}
