package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Marks a method as custom implementation of callback hook.
 * The method must return a {@link router.fu.MatchResult} and may accept up to three parameters:
 *
 * <ul>
 * <li>String parameter: Identifies the location matched.</li>
 * <li>{@link router.fu.Route} parameter: Identifies the route matched.</li>
 * <li>Map<String, String> parameter: Identifies the parameters extracted during match.</li>
 * </ul>
 */
@Documented
@Target( ElementType.METHOD )
public @interface RouteCallback
{
  /**
   * The name of the route that this is custom callback for.
   * If name is unspecified then the name of the method is assumed to be of the form
   * "[name]Callback" and can be derived.
   */
  @Nonnull
  String name() default "<default>";
}
