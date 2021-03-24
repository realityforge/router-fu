package router.fu;

import akasha.core.RegExpResult;
import java.util.Objects;
import javax.annotation.Nonnull;

final class BrowserMatchResult
  implements RegExMatchResult
{
  @Nonnull
  private final RegExpResult _groups;

  BrowserMatchResult( @Nonnull final RegExpResult groups )
  {
    _groups = Objects.requireNonNull( groups );
  }

  @Override
  public int getGroupCount()
  {
    return _groups.length();
  }

  @Override
  public String getGroup( final int i )
  {
    return _groups.getAt( i );
  }
}
