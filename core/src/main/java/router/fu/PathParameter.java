package router.fu;

import elemental2.core.RegExp;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Representation of a path parameter.
 */
public final class PathParameter
{
  @Nonnull
  private final String _name;
  @Nullable
  private final RegExp _validator;

  public PathParameter( @Nonnull final String name )
  {
    this( name, null );
  }

  public PathParameter( @Nonnull final String name, @Nullable final RegExp validator )
  {
    _name = Objects.requireNonNull( name );
    _validator = validator;
  }

  /**
   * Return the name of the parameter.
   *
   * @return the name of the parameter.
   */
  @Nonnull
  public String getName()
  {
    return _name;
  }

  /**
   * Return the validator for parameter if any.
   *
   * @return the validator for parameter if any.
   */
  @Nullable
  public RegExp getValidator()
  {
    return _validator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return getName();
  }
}
