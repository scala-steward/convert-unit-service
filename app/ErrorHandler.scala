import javax.inject.{Inject, Provider}
import org.slf4j.LoggerFactory
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.http.Status._
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import play.core.SourceMapper

import scala.concurrent._

/**
 * Handle all errors throw by controllers, handlers or routers.
 */
class ErrorHandler(environment: Environment, configuration: Configuration,
                   sourceMapper: Option[SourceMapper] = None, optionRouter: => Option[Router] = None)
  extends DefaultHttpErrorHandler(environment, configuration, sourceMapper, optionRouter) {

  private val logger = LoggerFactory.getLogger("application.ErrorHandler")

  @Inject
  def this(environment: Environment, configuration: Configuration, sourceMapper: OptionalSourceMapper,
           router: Provider[Router]) = {
    this(environment, configuration, sourceMapper.sourceMapper, Some(router.get))
  }

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.debug(s"onClientError: statusCode = $statusCode, uri = ${request.uri}, message = $message")

    Future.successful {
      val result = statusCode match {
        case BAD_REQUEST =>
          Results.BadRequest(message)
        case FORBIDDEN =>
          Results.Forbidden(message)
        case NOT_FOUND =>
          Results.NotFound(message)
        case clientError if statusCode >= 400 && statusCode < 500 =>
          Results.Status(statusCode)
        case nonClientError =>
          throw new IllegalArgumentException(s"onClientError invoked with non client error status code $statusCode: $message")
      }
      result
    }
  }

  override protected def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    val message = exception.toString
    if (message.toLowerCase.contains("evolution"))
      super.onDevServerError(request, exception)
    else
      Future.successful(InternalServerError(Json.obj("exception" -> message)))
  }

  override protected def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    Future.successful(InternalServerError)
  }

}
