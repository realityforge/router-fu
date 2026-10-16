package router.fu.example;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;

@ArezComponent
public abstract class AuthState {
    private @org.jspecify.annotations.Nullable String _username;

    @Observable
    public @org.jspecify.annotations.Nullable String getUsername() {
        return _username;
    }

    public void setUsername(final @org.jspecify.annotations.Nullable String username) {
        _username = username;
    }
}
