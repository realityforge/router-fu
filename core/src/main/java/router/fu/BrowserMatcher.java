package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Js;

final class BrowserMatcher
  implements RegExMatcher
{
  @Nonnull
  private final JsRuntime.RegExp _matcher;

  BrowserMatcher( @Nonnull final String pattern )
  {
    _matcher = new JsRuntime.RegExp( Objects.requireNonNull( pattern ) );
  }

  BrowserMatcher( @Nonnull final Object matcher )
  {
    _matcher = Js.uncheckedCast( Objects.requireNonNull( matcher ) );
  }

  @Nullable
  @Override
  public RegExMatchResult match( @Nonnull final String text )
  {
    final JsRuntime.RegExpResult result = _matcher.exec( text );
    return null == result ? null : new BrowserMatchResult( result );
  }
}
