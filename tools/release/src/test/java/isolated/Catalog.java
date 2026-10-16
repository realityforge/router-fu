package isolated;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.Router;

@NullMarked
@Router
@Route(name = "item", path = "items/:id")
@BoundParameter(name = "id")
class Catalog {}
