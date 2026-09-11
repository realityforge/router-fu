package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;

final class BrowserMatchResult
  implements RegExMatchResult
{
  @Nonnull
  private final JsRuntime.RegExpResult _groups;

  BrowserMatchResult( @Nonnull final JsRuntime.RegExpResult groups )
  {
    _groups = Objects.requireNonNull( groups );
  }

  @Override
  public int getGroupCount()
  {
    return _groups.getLength();
  }

  @Override
  public String getGroup( final int i )
  {
    return _groups.getAt( i );
  }
}
