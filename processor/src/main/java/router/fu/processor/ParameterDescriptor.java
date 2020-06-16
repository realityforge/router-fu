package router.fu.processor;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class ParameterDescriptor
{
  @Nonnull
  private final String _name;
  @Nullable
  private final String _constraint;

  ParameterDescriptor( @Nonnull final String name, @Nullable final String constraint )
  {
    _name = Objects.requireNonNull( name );
    _constraint = constraint;
  }

  @Nonnull
  String getKey()
  {
    return getName() + ( null == _constraint ? "" : "<" + _constraint + ">" );
  }

  @Nonnull
  String getFieldName()
  {
    return getName() + ( null == _constraint ? "" : "_" + Math.abs( _constraint.hashCode() ) );
  }

  @Nonnull
  String getName()
  {
    return _name;
  }

  @Nullable
  String getConstraint()
  {
    return _constraint;
  }
}
