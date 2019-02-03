package router.fu;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ParameterTest
  extends AbstractRouterFuTest
{
  @Test
  public void basicOperation()
  {
    final String name = ValueUtil.randomString();
    final TestRegExp validator = new TestRegExp();
    final Parameter parameter = new Parameter( name, validator );
    assertEquals( parameter.getName(), name );
    assertEquals( parameter.getValidator(), validator );
    assertEquals( parameter.toString(), name );
  }

  @Test
  public void nullValidator()
  {
    final String name = ValueUtil.randomString();
    final Parameter parameter = new Parameter( name );
    assertEquals( parameter.getName(), name );
    assertNull( parameter.getValidator() );
    assertEquals( parameter.toString(), name );
  }
}
