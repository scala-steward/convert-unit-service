package v1.units

import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Result}

import scala.concurrent.{ExecutionContext, Future}

class UnitsController @Inject()(ucc: UnitsControllerComponents)(
  implicit ec: ExecutionContext)
  extends UnitsBaseController(ucc) {

  def convertToSI: Action[AnyContent] = ApiAction.async { implicit request =>
    request.getQueryString("units").map(success) getOrElse failure
  }

  private def failure: Future[Result] =
    Future.successful(badRequestWithError("Empty unit query string."))

  private def success(unit: String): Future[Result] =
    this.handler.convertToSI(unit)
      .map(response => Ok(Json.toJson(response)))
      .recover { case error: Error => badRequestWithError(error.getMessage) }

}
