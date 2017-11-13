package router.fu;

import elemental2.core.RegExp;
import javax.annotation.Nonnull;

public class TestRegExp
  extends RegExp
{
  private String[] _resultGroups;

  public TestRegExp( @Nonnull final String[] resultGroups )
  {
    this();
    setResultGroups( resultGroups );
  }

  public TestRegExp()
  {
    super( new Object() );
  }

  public void setResultGroups( final String[] resultGroups )
  {
    _resultGroups = resultGroups;
  }

  @Override
  public String[] exec( final Object str )
  {
    return _resultGroups;
  }
}
