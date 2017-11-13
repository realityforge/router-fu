package router.fu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import static org.realityforge.braincheck.Guards.*;

public final class PathElement
{
  @Nullable
  private final PathParameter _parameter;
  @Nullable
  private final String _path;

  public PathElement( @Nullable final PathParameter parameter, @Nullable final String path )
  {
    _parameter = parameter;
    _path = path;
    assert null != _parameter || null != _path;
  }

  @Nonnull
  public String getPath()
  {
    apiInvariant( () -> null != _path,
                  () -> "PathElement.getParameter() invoked on parameter path element with parameter named '" +
                        _parameter + "'" );
    assert null != _path;
    return _path;
  }

  @Nonnull
  public PathParameter getParameter()
  {
    apiInvariant( () -> null != _parameter,
                  () -> "PathElement.getParameter() invoked on non-parameter path element with " +
                        "value '" + _path + "'" );
    assert null != _parameter;
    return _parameter;
  }

  public boolean isParameter()
  {
    return null != _parameter;
  }
}
