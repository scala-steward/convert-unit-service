package v1.units

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
 * Router to handle HTTP requests to make actions over the controller.
 * Some of the actions are:
 *    1) Convert to SI.
 */
class UnitsRouter @Inject()(controller: UnitsController) extends SimpleRouter {

  override def routes: Routes = {
    case GET(p"/") =>
      this.controller.convertToSI
  }

}
