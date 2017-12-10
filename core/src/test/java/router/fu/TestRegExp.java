package router.fu;

import elemental2.core.JsRegExp;
import javax.annotation.Nullable;

public class TestRegExp
  extends JsRegExp
{
  private String[] _resultGroups;

  TestRegExp( @Nullable final String[] resultGroups )
  {
    super( new Object() );
    _resultGroups = resultGroups;
  }

  TestRegExp()
  {
    this( null );
  }

  @Override
  public String[] exec( final String str )
  {
    return _resultGroups;
  }

  @Override
  public boolean test( final String str )
  {
    return null != _resultGroups;
  }
}
