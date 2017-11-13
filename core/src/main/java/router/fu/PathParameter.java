package router.fu;

import elemental2.core.RegExp;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class PathParameter
{
  @Nonnull
  private final String _name;
  @Nullable
  private final RegExp _validator;

  public PathParameter( @Nonnull final String name, @Nullable final RegExp validator )
  {
    _name = Objects.requireNonNull( name );
    _validator = validator;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @Nullable
  public RegExp getValidator()
  {
    return _validator;
  }
}
