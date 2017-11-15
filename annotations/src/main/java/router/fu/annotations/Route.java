package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Define a route in the router.
 * The order in which these routes are defined on the type is the order in which they applied in the router.
 */
@Documented
@Target( ElementType.TYPE )
@Repeatable( Routes.class )
public @interface Route
{
  /**
   * The name of the route.
   * This name must conform to the requirements of a java identifier.
   *
   * @return the name of the route.
   */
  @Nonnull
  String name();

  /**
   * The path pattern to match against. The echanisms for defining parameters are as follows:
   *
   * <ul>
   * <li><b>:param</b>: for URL parameters.</li>
   * <li><b>;param</b>: for matrix parameters.</li>
   * </ul>
   *
   * <p>For URL parameters and matrix parameters, you can add a constraint in the form of a regular expression.
   * Note that back slashes have to be escaped.</p>
   *
   * <ul>
   * <li><b>:param&lt;\\d+&gt;</b> will match numbers only for parameter param</p>
   * <li><b>;id&lt;[a-fA-F0-9]{8}&gt;</b> will match 8 characters hexadecimal strings for parameter id.</li>
   * </ul>
   */
  @Nonnull
  String path();

  /**
   * Return true if this route can be navigated to.
   *
   * @return true if this route can be navigated to.
   */
  boolean navigationTarget() default true;

  /**
   * Return true if the path pattern must match the start of the location, false if the path pattern must match the entire location.
   *
   * @return true if the path pattern must match the start of the location, false if the path pattern must match the entire location.
   */
  boolean partialMatch() default false;
}

