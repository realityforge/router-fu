package router.fu;

import akasha.core.RegExp;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Representation of a path parameter.
 */
public final class Parameter
{
  @Nonnull
  private final String _name;
  @Nullable
  private final RegExMatcher _validator;

  public Parameter( @Nonnull final String name )
  {
    this( name, (RegExMatcher) null );
  }

  public Parameter( @Nonnull final String name, @Nullable final RegExp validator )
  {
    this( name, null == validator ? null : new BrowserMatcher( validator ) );
  }

  Parameter( @Nonnull final String name, @Nullable final RegExMatcher validator )
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
  public RegExMatcher getValidator()
  {
    return _validator;
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
