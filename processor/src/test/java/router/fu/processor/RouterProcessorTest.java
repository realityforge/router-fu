package router.fu.processor;

import arez.processor.ArezProcessor;
import java.util.Arrays;
import javax.annotation.Nonnull;
import javax.annotation.processing.Processor;
import javax.tools.JavaFileObject;
import org.realityforge.proton.qa.AbstractProcessorTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class RouterProcessorTest
  extends AbstractProcessorTest
{
  @DataProvider( name = "successfulCompiles" )
  public Object[][] successfulCompiles()
  {
    return new Object[][]
      {
        new Object[]{ "com.example.arez.ArezRouter" },
        new Object[]{ "com.example.arez.CompleteRouter" },
        new Object[]{ "com.example.callback.BasicCallback" },
        new Object[]{ "com.example.callback.CallbackWithCustomName" },
        new Object[]{ "com.example.callback.CallbackWithParams1" },
        new Object[]{ "com.example.callback.CallbackWithParams2" },
        new Object[]{ "com.example.callback.CallbackWithParams3" },
        new Object[]{ "com.example.callback.CallbackWithParams4" },
        new Object[]{ "com.example.callback.CallbackWithParams5" },
        new Object[]{ "com.example.callback.CallbackWithParams6" },
        new Object[]{ "com.example.parameter.BasicBoundParameter" },
        new Object[]{ "com.example.parameter.URIEncodedParameter" },
        new Object[]{ "com.example.parameter.MultiRouteBoundParameter" },
        new Object[]{ "com.example.parameter.NonDefaultNameParameter" },
        new Object[]{ "com.example.parameter.SelectiveMultiRouteBoundParameter" },
        new Object[]{ "com.example.route.RouteParametersWithShortName" },
        new Object[]{ "com.example.route.RouteWithEmptyPath" },
        new Object[]{ "com.example.route.RouteWithLongStaticSegment" },
        new Object[]{ "com.example.route.RouteWithNonTargetParameters" },
        new Object[]{ "com.example.route.RouteWithOptionalParameters" },
        new Object[]{ "com.example.route.RouteWithParameters" },
        new Object[]{ "com.example.route.RouteWithParametersWithConstraints" },
        new Object[]{ "com.example.route.RouteWithPartialMatchParameters" },
        new Object[]{ "com.example.router.BasicRouter" },
        new Object[]{ "com.example.router.RouterWithTypeParams" },
        new Object[]{ "com.example.router_ref.AnnotatedRouterRef" },
        new Object[]{ "com.example.router_ref.BasicRouterRef" },
        new Object[]{ "com.example.router_ref.MultiRouterRef" },
        new Object[]{ "com.example.router_ref.ProtectedRouterRef" },
        new Object[]{ "com.example.router_ref.PublicRouterRef" }
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
    assertSuccessfulCompile( "com.example.nested.NestedRouter",
                             "com/example/nested/NestedRouter_InnerRouterService.java",
                             "com/example/nested/NestedRouter_RouterFu_InnerRouter.java" );
  }

  @Test
  public void callbackFromInterface()
    throws Exception
  {
    final JavaFileObject source1 = fixture( "input/com/example/callback/CallbackFromInterface.java" );
    final JavaFileObject source2 = fixture( "input/com/example/callback/CallbackInterface.java" );
    assertSuccessfulCompile( Arrays.asList( source1, source2 ),
                             Arrays.asList( "com/example/callback/CallbackFromInterfaceService.java",
                                            "com/example/callback/RouterFu_CallbackFromInterface.java" ) );
  }

  @DataProvider( name = "failedCompiles" )
  public Object[][] failedCompiles()
  {
    return new Object[][]
      {
        new Object[]{ "com.example.callback.BadParameterCallback",
                      "@RouteCallback target has unexpected parameter named 'myParam' that does not an expected type. Actual type: int" },
        new Object[]{ "com.example.callback.DuplicateCallback",
                      "@RouteCallback target duplicates an existing route callback method named 'regionCallback'route exists." },
        new Object[]{ "com.example.callback.DuplicateLocationParameterCallback",
                      "@RouteCallback target has two 'location' parameters named 'location' and 'location2'" },
        new Object[]{ "com.example.callback.DuplicateParametersParameterCallback",
                      "@RouteCallback target has two 'parameters' parameters named 'parameters' and 'parameters2'" },
        new Object[]{ "com.example.callback.DuplicateRouteParameterCallback",
                      "@RouteCallback target has two 'route' parameters named 'route' and 'route2'" },
        new Object[]{ "com.example.callback.FinalCallback", "@RouteCallback target must not be final" },
        new Object[]{ "com.example.callback.NameNotMatchRouteCallback",
                      "@RouteCallback target has name 'region' but no corresponding route exists." },
        new Object[]{ "com.example.callback.NoDeriveNameCallback",
                      "@RouteCallback target has not specified a name and is not named according to pattern '[Name]Callback'" },
        new Object[]{ "com.example.callback.PrivateCallback", "@RouteCallback target must not be private" },
        new Object[]{ "com.example.callback.StaticCallback", "@RouteCallback target must not be static" },
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
        new Object[]{ "com.example.route.UnmatchedOptionalParametersRouter",
                      "@Route named 'root' declares an optionalParameters that are not defined as part of the path: '/some/path/:foo', optionalParameters: a,z" },
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
        new Object[]{ "com.example.router_ref.BadType2RouterRef",
                      "Method annotated with @RouterRef must return an instance of com.example.router_ref.BadType2RouterRefService" },
        new Object[]{ "com.example.router_ref.BadTypeRouterRef",
                      "Method annotated with @RouterRef must return an instance of com.example.router_ref.BadTypeRouterRefService" },
        new Object[]{ "com.example.router_ref.ExceptionRouterRef", "@RouterRef target must not throw any exceptions" },
        new Object[]{ "com.example.router_ref.FinalRouterRef", "@RouterRef target must not be final" },
        new Object[]{ "com.example.router_ref.ParametersRouterRef", "@RouterRef target must not have any parameters" },
        new Object[]{ "com.example.router_ref.PrivateRouterRef", "@RouterRef target must not be private" },
        new Object[]{ "com.example.router_ref.StaticRouterRef", "@RouterRef target must not be static" }
      };
  }

  @Test( dataProvider = "failedCompiles" )
  public void processFailedCompile( @Nonnull final String classname, @Nonnull final String errorMessageFragment )
  {
    assertFailedCompile( classname, errorMessageFragment );
  }

  @Nonnull
  @Override
  protected Processor processor()
  {
    return new RouterProcessor();
  }

  @Nonnull
  @Override
  protected Processor[] additionalProcessors()
  {
    return new Processor[]{ new ArezProcessor() };
  }

  @Nonnull
  @Override
  protected String getOptionPrefix()
  {
    return "router.fu";
  }

  @Override
  protected boolean emitGeneratedFile( @Nonnull final String target )
  {
    return super.emitGeneratedFile( target ) &&
           !target.contains( "/Arez_" ) &&
           !target.contains( "_Arez_" ) &&
           !target.startsWith( "/Arez_" );
  }

  void assertSuccessfulCompile( @Nonnull final String classname )
    throws Exception
  {
    assertSuccessfulCompile( classname,
                             toFilename( classname, "RouterFu_", ".java" ),
                             toFilename( classname, "", "Service.java" ) );
  }
}
