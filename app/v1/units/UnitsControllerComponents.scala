package v1.units

import config.{ApiActionBuilder, RequestMarkerContext}
import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.http.FileMimeTypes
import play.api.i18n.{Langs, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc._

/**
 * Packages component dependencies for the [[UnitsController]].
 *
 * This is a good way to minimize the exposed surface area
 * to the controller, so the controller only needs to have one thing injected.
 */
case class UnitsControllerComponents @Inject()(apiActionBuilder: ApiActionBuilder,
                                               cache: AsyncCacheApi,
                                               handler: UnitsHandler,
                                               actionBuilder: DefaultActionBuilder,
                                               parsers: PlayBodyParsers,
                                               messagesApi: MessagesApi,
                                               langs: Langs,
                                               fileMimeTypes: FileMimeTypes,
                                               executionContext: scala.concurrent.ExecutionContext)
  extends ControllerComponents

/**
 * Exposes actions and handler to the [[UnitsController]], connecting the injected state to the base class.
 */
class UnitsBaseController @Inject()(ucc: UnitsControllerComponents)
  extends BaseController
    with RequestMarkerContext {

  override protected def controllerComponents: ControllerComponents = ucc

  def ApiAction: ApiActionBuilder = ucc.apiActionBuilder

  def handler: UnitsHandler = ucc.handler

  def cacheApi: AsyncCacheApi = ucc.cache

  def badRequestWithError(error: String): Result =
    BadRequest(Json.obj("error" -> error))

}
