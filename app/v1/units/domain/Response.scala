package v1.units.domain

import play.api.libs.json.{Format, Json}

object Response {

  case class ConversionResponse(unit_name: String, multiplication_factor: BigDecimal)

  object ConversionResponse {
    implicit val format: Format[ConversionResponse] = Json.format
  }

}
