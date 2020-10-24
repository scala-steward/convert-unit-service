package v1.units

import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, Result}

import scala.concurrent.Future

class UnitsController @Inject()(implicit ucc: UnitsControllerComponents)
  extends UnitsBaseController {

  def convertToSI: Action[AnyContent] = ApiAction.async { implicit request =>
    request.getQueryString("units").map(success) getOrElse failure
  }

  private def failure: Future[Result] =
    Future.successful(badRequestWithError("Empty unit query string."))

  private def success(unit: String): Future[Result] =
    Future.successful(Ok)

}
