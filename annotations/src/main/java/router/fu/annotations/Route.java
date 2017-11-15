package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

@Documented
@Target( ElementType.TYPE )
@Repeatable( Routes.class )
public @interface Route
{
  enum MatchType
  {
    FULL,
    START,
    REGEX
  }

  @Nonnull
  String name();

  @Nonnull
  String path();

  boolean navigationTarget() default true;

  MatchType matchType() default MatchType.FULL;
}

