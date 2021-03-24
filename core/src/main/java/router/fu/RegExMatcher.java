package router.fu;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

interface RegExMatcher
{
  @Nullable
  RegExMatchResult match( @Nonnull String text );
}
