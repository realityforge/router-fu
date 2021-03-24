package router.fu;

import javax.annotation.Nonnull;

public class TestRegExpResult
  implements RegExMatchResult
{
  private final String[] _resultGroups;

  TestRegExpResult( @Nonnull final String[] resultGroups )
  {
    _resultGroups = resultGroups;
  }

  @Override
  public int getGroupCount()
  {
    return _resultGroups.length;
  }

  @Override
  public String getGroup( final int i )
  {
    return _resultGroups[ i ];
  }
}
