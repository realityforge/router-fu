package router.fu;

import javax.annotation.Nonnull;
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
}
