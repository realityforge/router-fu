package router.fu.example;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import org.jspecify.annotations.Nullable;

@ArezComponent
public abstract class AuthState {
    private @Nullable String _username;

    @Observable
    public @Nullable String getUsername() {
        return _username;
    }

    public void setUsername(final @Nullable String username) {
        _username = username;
    }
}
