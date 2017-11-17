package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Identifies a parameter that should be exposed via router.
 */
@Documented
@Target( ElementType.TYPE )
@Repeatable( BoundParameters.class )
public @interface BoundParameter
{
  /**
   * The name under which to expose parameter.
   *
   * @return the name under which to expose parameter.
   */
  @Nonnull
  String name();

  /**
   * The name of the parameter.
   * If not specified this defaults to the {@link #name()} value.
   *
   * @return the name of the parameter or the sentinel value.
   */
  @Nonnull
  String parameterName() default "";

  /**
   * Return the names of the routes where this parameter should be extracted.
   * If not specified then it is assumed that every route that has a matching parameter name should be included.
   *
   * @return the names of the routes where this parameter should be extracted.
   */
  @Nonnull
  String[] routeNames() default {};
}
