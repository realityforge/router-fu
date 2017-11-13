package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import static org.realityforge.braincheck.Guards.*;

/**
 * This class represents the path segments used when reconstructing paths for route.
 */
public final class PathElement
{
  @Nullable
  private final PathParameter _parameter;
  @Nullable
  private final String _path;

  /**
   * Create a path element for a static path component.
   *
   * @param path the static path component.
   */
  public PathElement( @Nonnull final String path )
  {
    _parameter = null;
    _path = Objects.requireNonNull( path );
  }

  /**
   * Create a path element for a parameter.
   *
   * @param parameter the parameter.
   */
  public PathElement( @Nonnull final PathParameter parameter )
  {
    _parameter = Objects.requireNonNull( parameter );
    _path = null;
  }

  /**
   * Return the static path segment. This should not be called if {@link #isParameter()} returns true.
   *
   * @return the static path component.
   */
  @Nonnull
  public String getPath()
  {
    apiInvariant( () -> null != _path,
                  () -> "PathElement.getParameter() invoked on parameter path element with parameter named '" +
                        _parameter + "'" );
    assert null != _path;
    return _path;
  }

  /**
   * Return the parameter used to construct segment. This should only be called if {@link #isParameter()} returns true.
   *
   * @return the parameter component.
   */
  @Nonnull
  public PathParameter getParameter()
  {
    apiInvariant( () -> null != _parameter,
                  () -> "PathElement.getParameter() invoked on non-parameter path element with " +
                        "value '" + _path + "'" );
    assert null != _parameter;
    return _parameter;
  }

  /**
   * Return true if this path element is a parameter.
   *
   * @return true if this path element is a parameter.
   */
  public boolean isParameter()
  {
    return null != _parameter;
  }
}
