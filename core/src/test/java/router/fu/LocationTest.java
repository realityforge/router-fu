package router.fu;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LocationTest
  extends AbstractRouterFuTest
{
  @Test
  public void basicOperation()
  {
    final RouteState state1 = newRouteState( false );
    final RouteState state2 = newRouteState( false );
    final RouteState state3 = newRouteState( false );
    final RouteState state4 = newRouteState( true );

    final String path = ValueUtil.randomString();
    final Location location = new Location( path, Arrays.asList( state1, state2, state3, state4 ) );
    assertEquals( location.getPath(), path );
    assertEquals( location.getStates().size(), 4 );
    assertEquals( location.getStates().get( 0 ), state1 );
    assertEquals( location.getStates().get( 1 ), state2 );
    assertEquals( location.getStates().get( 2 ), state3 );
    assertEquals( location.getStates().get( 3 ), state4 );
    assertEquals( location.getTerminalState(), state4 );
  }

  @Test
  public void noTerminal()
  {
    final RouteState state1 = newRouteState( false );
    final RouteState state2 = newRouteState( false );

    final Location location = new Location( ValueUtil.randomString(), Arrays.asList( state1, state2 ) );
    assertEquals( location.getStates().size(), 2 );
    assertEquals( location.getStates().get( 0 ), state1 );
    assertEquals( location.getStates().get( 1 ), state2 );
    assertNull( location.getTerminalState() );
  }

  @Test
  public void noState()
  {
    final Location location = new Location( ValueUtil.randomString(), Collections.emptyList() );
    assertEquals( location.getStates().size(), 0 );
    assertNull( location.getTerminalState() );
  }

  @Nonnull
  private RouteState newRouteState( final boolean terminal )
  {
    return new RouteState( newRoute(), new HashMap<>(), terminal );
  }

  @Nonnull
  private Route newRoute()
  {
    return new Route( ValueUtil.randomString(),
                      new Segment[ 0 ],
                      new Parameter[ 0 ],
                      new TestRegExp(),
                      new TestRouteMatchCallback() );
  }
}
