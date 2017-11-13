package router.fu;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class PathParameterTest
{
  @Test
  public void basicOperation()
  {
    final String name = ValueUtil.randomString();
    final TestRegExp validator = new TestRegExp();
    final PathParameter parameter = new PathParameter( name, validator );
    assertEquals( parameter.getName(), name );
    assertEquals( parameter.getValidator(), validator );
    assertEquals( parameter.toString(), name );
  }

  @Test
  public void nullValidator()
  {
    final String name = ValueUtil.randomString();
    final PathParameter parameter = new PathParameter( name );
    assertEquals( parameter.getName(), name );
    assertEquals( parameter.getValidator(), null );
    assertEquals( parameter.toString(), name );
  }
}
