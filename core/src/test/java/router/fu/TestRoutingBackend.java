package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import static org.testng.Assert.*;

final class TestRoutingBackend
  implements RoutingBackend
{
  private LocationChangeListener _handler;
  @Nonnull
  private String _location = "";

  @Nonnull
  @Override
  public String getLocation()
  {
    return _location;
  }

  @Override
  public void setLocation( @Nonnull final String location )
  {
    _location = Objects.requireNonNull( location );
  }

  @Nonnull
  @Override
  public Object addListener( @Nonnull final LocationChangeListener handler )
  {
    _handler = handler;
    return handler;
  }

  @Override
  public void removeListener( @Nonnull final Object handle )
  {
    if ( null != _handler )
    {
      assertEquals( _handler, handle );
      _handler = null;
    }
  }

  @Nullable
  LocationChangeListener getHandler()
  {
    return _handler;
  }
}
