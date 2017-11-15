package router.fu;

import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class SegmentTest
{
  @Test
  public void staticPath()
  {
    final String path = ValueUtil.randomString();
    final Segment segment = new Segment( path );

    assertEquals( segment.isParameter(), false );
    assertEquals( segment.getPath(), path );

    final IllegalStateException exception = expectThrows( IllegalStateException.class, segment::getParameter );
    assertEquals( exception.getMessage(),
                  "Segment.getParameter() invoked on non-parameter path element with value '" + path + "'" );
  }

  @Test
  public void parameterPath()
  {
    final String parameterName = ValueUtil.randomString();
    final Parameter parameter = new Parameter( parameterName, null );
    final Segment segment = new Segment( parameter );

    assertEquals( segment.isParameter(), true );
    assertEquals( segment.getParameter(), parameter );

    final IllegalStateException exception = expectThrows( IllegalStateException.class, segment::getPath );
    assertEquals( exception.getMessage(),
                  "Segment.getPath() invoked on parameter path element with parameter named '" +
                  parameterName + "'" );
  }
}
