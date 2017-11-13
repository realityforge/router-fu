package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;

public final class PathElement
{
  @Nonnull
  private final String _value;
  private final boolean _parameter;

  public PathElement( @Nonnull final String value, final boolean parameter )
  {
    _value = Objects.requireNonNull( value );
    _parameter = parameter;
  }

  @Nonnull
  public String getValue()
  {
    return _value;
  }

  public boolean isParameter()
  {
    return _parameter;
  }
}
