package v1.units

import javax.inject.Inject
import v1.units.domain.Response.ConvertResponse

import scala.concurrent.Future

class UnitsHandler @Inject()() {

  def convertToSI(unit: String): Future[ConvertResponse] = {
    // Mock response.
    Future.successful(ConvertResponse("(rad/s)", .00029088820866572))
  }

}
