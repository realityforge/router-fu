package router.fu;

import elemental2.core.RegExpResult;
import javax.annotation.Nonnull;

public class TestRegExpResult
  extends RegExpResult
{
  private String[] _resultGroups;

  TestRegExpResult( @Nonnull final String[] resultGroups )
  {
    length = resultGroups.length;
    _resultGroups = resultGroups;
  }

  @Override
  public String getAt( final int index )
  {
    return _resultGroups[ index ];
  }
}
