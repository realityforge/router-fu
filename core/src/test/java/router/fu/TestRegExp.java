package router.fu;

import elemental2.core.JsRegExp;
import elemental2.core.RegExpResult;
import javax.annotation.Nonnull;

public class TestRegExp
  extends JsRegExp
{
  private RegExpResult _resultGroups;

  TestRegExp( @Nonnull final String[] resultGroups )
  {
    super( new Object() );
    _resultGroups = new TestRegExpResult( resultGroups );
  }

  TestRegExp()
  {
    _resultGroups = null;
  }

  @Override
  public RegExpResult exec( final String str )
  {
    return _resultGroups;
  }

  @Override
  public boolean test( final String str )
  {
    return null != _resultGroups;
  }
}
