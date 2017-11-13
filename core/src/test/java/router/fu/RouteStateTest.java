package router.fu;

import java.util.HashMap;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RouteStateTest
{
  @Test
  public void basicOperation()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final Route route = new Route( ValueUtil.randomString(),
                                   new PathElement[ 0 ],
                                   new PathParameter[ 0 ],
                                   new TestRegExp(),
                                   matchCallback );
    final HashMap<String, String> parameters = new HashMap<>();
    final boolean terminal = true;
    final RouteState state = new RouteState( route, parameters, terminal );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters(), parameters );
    assertEquals( state.isTerminal(), terminal );
  }
}
