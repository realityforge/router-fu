package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks class to be processed by RouterFu annotation processor.
 */
@Documented
@Target( ElementType.TYPE )
public @interface Router
{
  /**
   * Return true if router should be an Arez component.
   *
   * @return true if router should be an Arez component.
   */
  boolean arez() default false;
}
