package router.fu.example;

import org.realityforge.arez.annotations.ArezComponent;
import org.realityforge.arez.annotations.Observable;

@ArezComponent
public class AuthState
{
  private String _username;

  @Observable
  public String getUsername()
  {
    return _username;
  }

  public void setUsername( final String username )
  {
    _username = username;
  }
}
