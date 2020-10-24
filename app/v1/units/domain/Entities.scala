package v1.units.domain

object Entities {

  sealed trait Expression
  case class MultiplyExpr(left: Expression, right: Expression) extends Expression
  case class DivideExpr(left: Expression, right: Expression) extends Expression
  case class UnitExpr(value: Unit) extends Expression

  sealed trait Unit {
    def symbol: String
    def quantity: String
    def nameSI: String
    def valueSI: BigDecimal
  }

  case object Minute extends Unit {
    override def symbol: String = "min"
    override def quantity: String = "time"
    override def nameSI: String = "s"
    override def valueSI: BigDecimal = 60
  }

  case object Hour extends Unit {
    override def symbol: String = "h"
    override def quantity: String = "time"
    override def nameSI: String = "s"
    override def valueSI: BigDecimal = 3600
  }

  case object Day extends Unit {
    override def symbol: String = "d"
    override def quantity: String = "time"
    override def nameSI: String = "s"
    override def valueSI: BigDecimal = 86400
  }

  case object Degree extends Unit {
    override def symbol: String = "°"
    override def quantity: String = "unitless/plane angle"
    override def nameSI: String = "rad"
    override def valueSI: BigDecimal = Math.PI / 180
  }

  case object ArcMinute extends Unit {
    override def symbol: String = "'"
    override def quantity: String = "unitless/plane angle"
    override def nameSI: String = "rad"
    override def valueSI: BigDecimal = Math.PI / 10800
  }

  case object ArcSecond extends Unit {
    override def symbol: String = "\""
    override def quantity: String = "unitless/plane angle"
    override def nameSI: String = "rad"
    override def valueSI: BigDecimal = Math.PI / 648000
  }

  case object Hectare extends Unit {
    override def symbol: String = "ha"
    override def quantity: String = "area"
    override def nameSI: String = "m"
    override def valueSI: BigDecimal = 10000
  }

  case object Litre extends Unit {
    override def symbol: String = "L"
    override def quantity: String = "volume"
    override def nameSI: String = "L"
    override def valueSI: BigDecimal = 0.001
  }

  case object Tonne extends Unit {
    override def symbol: String = "t"
    override def quantity: String = "mass"
    override def nameSI: String = "t"
    override def valueSI: BigDecimal = 1000
  }

  object Unit {

    def fromString(unit: String): Unit = unit.toLowerCase match {
      case "minute" | "min"   => Minute
      case "hour" | "h"       => Hour
      case "day" | "d"        => Day
      case "degree" | ""°""   => Degree
      case "arcminute" | "'"  => ArcMinute
      case "arcsecond" | "\"" => ArcSecond
      case "hectare" | "a"    => Hectare
      case "litre" | "L"      => Litre
      case "tonne" | "t"      => Tonne
      case _ => throw new Exception(s"Unmapped unit ($unit).")
    }

  }

}
