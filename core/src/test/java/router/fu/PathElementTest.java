package router.fu;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class PathElementTest
{
  @Test
  public void staticPath()
  {
    final String path = ValueUtil.randomString();
    final PathElement pathElement = new PathElement( path );

    assertEquals( pathElement.isParameter(), false );
    assertEquals( pathElement.getPath(), path );

    final IllegalStateException exception = expectThrows( IllegalStateException.class, pathElement::getParameter );
    assertEquals( exception.getMessage(),
                  "PathElement.getParameter() invoked on non-parameter path element with value '" + path + "'" );
  }

  @Test
  public void parameterPath()
  {
    final String parameterName = ValueUtil.randomString();
    final Parameter parameter = new Parameter( parameterName, null );
    final PathElement pathElement = new PathElement( parameter );

    assertEquals( pathElement.isParameter(), true );
    assertEquals( pathElement.getParameter(), parameter );

    final IllegalStateException exception = expectThrows( IllegalStateException.class, pathElement::getPath );
    assertEquals( exception.getMessage(),
                  "PathElement.getPath() invoked on parameter path element with parameter named '" +
                  parameterName + "'" );
  }
}
