package v1.units

import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Result}

import scala.concurrent.{ExecutionContext, Future}

/**
 * Handle all units operations and return HTTP responses.
 */
class UnitsController @Inject()(cacheApi: AsyncCacheApi, ucc: UnitsControllerComponents)(
  implicit ec: ExecutionContext)
  extends UnitsBaseController(ucc) {

  def convertToSI: Action[AnyContent] = ApiAction.async { implicit request =>
    request.getQueryString("units").map(success) getOrElse failure
  }

  /**
   * If it hasn't any "units" query string value then this method is call.
   * @return 400 Bad Request with error message.
   */
  private def failure: Future[Result] =
    Future.successful(badRequestWithError("Empty unit query string."))

  /**
   * If it has an "units" query string value then we'll convert to a SI value.
   * @return 200 OK with conversion json object - see more [[v1.units.domain.Response.ConversionResponse]].
   */
  private def success(unit: String): Future[Result] =
    this.cacheApi.getOrElseUpdate(s"conversion-to-$unit") {
      this.handler.convertToSI(unit)
        .map(response => Ok(Json.toJson(response)))
        .recover { case error: Error => badRequestWithError(error.getMessage) }
    }

}
