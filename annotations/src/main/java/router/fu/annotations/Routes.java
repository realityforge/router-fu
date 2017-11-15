package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * The annotation that contains the {@link Route} annotations.
 * This is not typically used but only used by virtual of repeatable annotations.
 */
@Documented
@Target( ElementType.TYPE )
public @interface Routes
{
  /**
   * Return the contained route annotations.
   *
   * @return the contained route annotations.
   */
  Route[] value();
}
