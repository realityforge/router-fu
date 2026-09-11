package router.fu;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsArrayLike;

final class BrowserMatcher
  implements RegExMatcher
{
  @Nonnull
  private final RegExp _matcher;

  BrowserMatcher( @Nonnull final String pattern )
  {
    _matcher = new RegExp( Objects.requireNonNull( pattern ) );
  }

  BrowserMatcher( @Nonnull final Object matcher )
  {
    _matcher = Js.uncheckedCast( Objects.requireNonNull( matcher ) );
  }

  @Nullable
  @Override
  public RegExMatchResult match( @Nonnull final String text )
  {
    final JsArrayLike<String> result = _matcher.exec( text );
    return null == result ? null : new BrowserMatchResult( result );
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "RegExp" )
  private static final class RegExp
  {
    RegExp( String pattern )
    {
    }

    native JsArrayLike<String> exec( String text );
  }

  private static final class BrowserMatchResult
    implements RegExMatchResult
  {
    @Nonnull
    private final JsArrayLike<String> _groups;

    private BrowserMatchResult( @Nonnull final JsArrayLike<String> groups )
    {
      _groups = groups;
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
}
