package router.fu.browser;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@NullMarked
@Router
@Route(name = "region", path = "regions/:id", optionalParameters = "id")
@BoundParameter(name = "id")
class BrowserRouter {}
