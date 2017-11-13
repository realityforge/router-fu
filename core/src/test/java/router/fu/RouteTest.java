package router.fu;

import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RouteTest
{
  @DataProvider( name = "pathToRegex" )
  public static Object[][] pathToRegexData()
  {
    return new Object[][]{
      { "X", "^X$" },
      { "/abc/1!", "^\\/abc\\/1!$" },
      { "/+$#-?()[]{}.*\\", "^\\/\\+\\$#\\-\\?\\(\\)\\[\\]\\{\\}\\.\\*\\\\$" },
      };
  }

  @Test( dataProvider = "pathToRegex" )
  public void pathToPattern( @Nonnull final String path, @Nonnull final String expected )
    throws Exception
  {
    assertEquals( Route.pathToPattern( path ), expected );
  }

  @Test
  public void getParameterByIndex_withBadIndex()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Route route =
      new Route( name,
                 new PathElement[ 0 ],
                 new PathParameter[ 0 ],
                 new TestRegExp(),
                 matchCallback );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.getParameterByIndex( 1 ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' expects a parameter at index 1 when matching location " +
                  "but no such parameter has been defined." );
  }
}
