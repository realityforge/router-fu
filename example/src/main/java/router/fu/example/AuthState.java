package router.fu.example;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class AuthState
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
