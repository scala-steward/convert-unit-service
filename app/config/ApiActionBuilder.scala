package config

import javax.inject.Inject
import net.logstash.logback.marker.LogstashMarker
import org.slf4j.LoggerFactory
import play.api.MarkerContext
import play.api.http.HttpVerbs
import play.api.i18n.MessagesApi
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


/**
 * A bundled request for API resources.
 * This is commonly used to store specific request information, such as security credentials and useful shortcut methods.
 */
trait ApiRequestHeader
  extends MessagesRequestHeader
    with PreferredMessagesProvider

class ApiRequest[A](request: Request[A], val messagesApi: MessagesApi)
  extends WrappedRequest(request)
    with ApiRequestHeader

/**
 * Provides an implicit marker that will show the request in all instructions in the log.
 */
trait RequestMarkerContext {

  import net.logstash.logback.marker.Markers

  private def marker(tuple: (String, Any)) = Markers.append(tuple._1, tuple._2)

  private implicit class RichLogstashMarker(marker1: LogstashMarker) {
    def &&(marker2: LogstashMarker): LogstashMarker = marker1.and(marker2)
  }

  implicit def requestHeaderToMarkerContext(implicit request: RequestHeader): MarkerContext = {
    MarkerContext {
      marker("id" -> request.id) && marker("host" -> request.host) && marker("remoteAddress" -> request.remoteAddress)
    }
  }

}

/**
 * The action builder for the API resources.
 * This is the place to place logs, metrics, to increase the request with contextual data and manipulate the result.
 */
class ApiActionBuilder @Inject()(messagesApi: MessagesApi, playBodyParsers: PlayBodyParsers)(
  implicit val executionContext: ExecutionContext)
  extends ActionBuilder[ApiRequest, AnyContent]
    with RequestMarkerContext
    with HttpVerbs {

  override val parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  type ApiRequestBlock[A] = ApiRequest[A] => Future[Result]

  private val logger = LoggerFactory.getLogger(getClass)

  override def invokeBlock[A](request: Request[A], block: ApiRequestBlock[A]): Future[Result] = {
    implicit val markerContext: MarkerContext = requestHeaderToMarkerContext(request)

    this.logger.debug(s"Request-Id: ${request.id}")
    this.logger.debug(s"Method: ${request.method}")
    this.logger.debug(s"Path: ${request.uri}")
    this.logger.debug(s"Body: ${request.body}")

    val future = block(new ApiRequest(request, messagesApi))

    future.map { result =>
      request.method match {
        case GET | HEAD =>
          result.withHeaders("Cache-Control" -> s"max-age: 100")
        case _ =>
          result
      }
    }
  }
}
