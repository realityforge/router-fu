package router.fu;

import java.util.HashMap;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RouteStateTest
  extends AbstractRouterFuTest
{
  @Test
  public void basicOperation()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final Route route = new Route( ValueUtil.randomString(),
                                   new Segment[ 0 ],
                                   new Parameter[ 0 ],
                                   new TestRegExp(),
                                   matchCallback );
    final HashMap<Parameter, String> parameters = new HashMap<>();
    final Parameter parameter = new Parameter( ValueUtil.randomString() );
    final String parameterValue = ValueUtil.randomString();
    parameters.put( parameter, parameterValue );
    final boolean terminal = true;
    final RouteState state = new RouteState( route, parameters, terminal );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters(), parameters );
    assertEquals( state.isTerminal(), terminal );
    assertEquals( state.getParameterValue( parameter ), parameterValue );
  }
}
