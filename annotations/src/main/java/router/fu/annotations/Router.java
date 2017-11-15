package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target( ElementType.TYPE )
public @interface Router
{
  boolean arez() default false;
}
