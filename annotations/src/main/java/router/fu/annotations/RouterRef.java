package router.fu.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Marks a template method that returns the service interface generated for router.
 *
 * <p>The method that is annotated with @RouterRef must also comply with the following constraints:</p>
 * <ul>
 * <li>Must not be private</li>
 * <li>Must not be static</li>
 * <li>Must not be final</li>
 * <li>Must not be abstract</li>
 * <li>Must not throw any exceptions</li>
 * <li>Must return an instance of service interface.</li>
 * </ul>
 */
@Documented
@Target( ElementType.METHOD )
public @interface RouterRef
{
}
