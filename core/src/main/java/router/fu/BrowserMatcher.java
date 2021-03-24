package router.fu;

import akasha.core.RegExp;
import akasha.core.RegExpResult;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class BrowserMatcher
  implements RegExMatcher
{
  @Nonnull
  private final RegExp _matcher;

  BrowserMatcher( @Nonnull final RegExp matcher )
  {
    _matcher = Objects.requireNonNull( matcher );
  }

  @Nullable
  @Override
  public RegExMatchResult match( @Nonnull final String text )
  {
    final RegExpResult result = _matcher.exec( text );
    return null == result ? null : new BrowserMatchResult( result );
  }
}
