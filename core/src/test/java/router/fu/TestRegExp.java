package router.fu;

import elemental2.core.RegExp;
import javax.annotation.Nullable;

public class TestRegExp
  extends RegExp
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
  public String[] exec( final Object str )
  {
    return _resultGroups;
  }

  @Override
  public boolean test( final Object str )
  {
    return null != _resultGroups;
  }
}
