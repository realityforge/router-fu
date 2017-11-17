package router.fu.processor;

import javax.annotation.Nonnull;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RouterProcessorTest
  extends AbstractRouterProcessorTest
{
  @DataProvider( name = "successfulCompiles" )
  public Object[][] successfulCompiles()
  {
    return new Object[][]
      {
        new Object[]{ "com.example.arez.ArezRouter" },
        new Object[]{ "com.example.arez.CompleteRouter" },
        new Object[]{ "com.example.route.RouteParametersWithShortName" },
        new Object[]{ "com.example.route.RouteWithNonTargetParameters" },
        new Object[]{ "com.example.route.RouteWithParameters" },
        new Object[]{ "com.example.route.RouteWithParametersWithConstraints" },
        new Object[]{ "com.example.route.RouteWithPartialMatchParameters" },
        new Object[]{ "com.example.router.BasicRouter" },
        new Object[]{ "com.example.router.RouterWithTypeParams" }
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
        new Object[]{ "com.example.parameter.BadParameterName",
                      "@Router target has a @BoundParameter with an invalid name ''" },
        new Object[]{ "com.example.parameter.DuplicateParameterName",
                      "@Router target has multiple @BoundParameter annotations with the name 'regionCode'" },
        new Object[]{ "com.example.parameter.NoSuchParameterInAnyRoute",
                      "@Router target has a @BoundParameter that specifies a parameter named 'regionCode' but parameter does not exist on any routes." },
        new Object[]{ "com.example.parameter.NoSuchParameterInSpecificRoute",
                      "@Router target has a @BoundParameter that specifies a route named 'region' for parameter named 'regionCode' but parameter does not exist." },
        new Object[]{ "com.example.parameter.NoSuchRoute",
                      "@Router target has a @BoundParameter that specifies a route named 'region' that does not exist." },
        new Object[]{ "com.example.route.BadParameterName2Router",
                      "@Router target has a route with an invalid name ''" },
        new Object[]{ "com.example.route.BadParameterName3Router",
                      "@Router target has a route with an invalid name 'ace-'" },
        new Object[]{ "com.example.route.BadParameterNameRouter",
                      "@Router target has a route with an invalid name '-ace'" },
        new Object[]{ "com.example.route.BadPathInParameterRouter",
                      "@Route named 'root' has a path that can not be parsed: '<>#kjskaj'" },
        new Object[]{ "com.example.route.SameParameterNameRouter",
                      "@Router target has multiple routes with the name 'root'" },
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
