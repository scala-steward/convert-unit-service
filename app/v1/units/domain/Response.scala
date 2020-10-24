package v1.units.domain

import play.api.libs.json.{Format, Json}

object Response {

  case class ConvertResponse(unit_name: String, multiplication_factor: BigDecimal)

  object ConvertResponse {
    implicit val format: Format[ConvertResponse] = Json.format
  }

}
