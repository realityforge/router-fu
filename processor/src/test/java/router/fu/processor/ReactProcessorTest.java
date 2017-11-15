package router.fu.processor;

import javax.annotation.Nonnull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReactProcessorTest
  extends AbstractRouterProcessorTest
{
  @DataProvider( name = "successfulCompiles" )
  public Object[][] successfulCompiles()
  {
    return new Object[][]
      {
        new Object[]{ "com.example.arez.ArezRouter" },
        new Object[]{ "com.example.router.BasicRouter" }
      };
  }

  @Test( dataProvider = "successfulCompiles" )
  public void processSuccessfulCompile( @Nonnull final String classname )
    throws Exception
  {
    assertSuccessfulCompile( classname );
  }

  @Test
  public void nestedReactComponent()
    throws Exception
  {
    assertSuccessfulCompile( "input/com/example/nested/NestedRouter.java",
                             "expected/com/example/nested/NestedRouter$InnerRouterService.java",
                             "expected/com/example/nested/NestedRouter$RouterFu_InnerRouter.java" );
  }

  @DataProvider( name = "failedCompiles" )
  public Object[][] failedCompiles()
  {
    return new Object[][]
      {
        new Object[]{ "com.example.router.AbstractRouter", "@Router target must not be abstract" },
        new Object[]{ "com.example.router.CtorWithArgsRouter",
                      "@Router target must have a single non-private, no-argument constructor or the default constructor" },
        new Object[]{ "com.example.router.EnumRouter", "@Router target must be a class" },
        new Object[]{ "com.example.router.FinalRouter", "@Router target must not be final" },
        new Object[]{ "com.example.router.InterfaceRouter", "@Router target must be a class" },
        new Object[]{ "com.example.router.NonStaticNestedRouter",
                      "@Router target must not be a non-static nested class" },
        new Object[]{ "com.example.router.PrivateCtorRouter",
                      "@Router target must have a single non-private, no-argument constructor or the default constructor" },
        };
  }

  @Test( dataProvider = "failedCompiles" )
  public void processFailedCompile( @Nonnull final String classname, @Nonnull final String errorMessageFragment )
    throws Exception
  {
    assertFailedCompile( classname, errorMessageFragment );
  }
}