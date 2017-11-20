package router.fu.example;

final class AppState
{
  static final AuthState AUTH = new Arez_AuthState();
  static final MyRouterService ROUTER = MyRouter.create();

  private AppState()
  {
  }
}
