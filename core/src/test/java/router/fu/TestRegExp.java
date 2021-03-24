package router.fu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestRegExp
  implements RegExMatcher
{
  @Nullable
  private final RegExMatchResult _resultGroups;

  TestRegExp( @Nonnull final String... resultGroups )
  {
    _resultGroups = new TestRegExpResult( resultGroups );
  }

  TestRegExp()
  {
    _resultGroups = null;
  }

  @Nullable
  @Override
  public RegExMatchResult match( @Nonnull final String text )
  {
    return _resultGroups;
  }
}
