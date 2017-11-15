package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

@Documented
@Target( ElementType.TYPE )
@Repeatable( RouteParameters.class )
public @interface RouteParameter
{
  @Nonnull
  String name();

  @Nonnull
  String field() default "<default>";

  @Nonnull
  String[] routeNames() default {};

}
