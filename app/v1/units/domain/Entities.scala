package v1.units.domain

object Entities {

  sealed trait Expression {
    def name(): String
    def multiplicationFactor(): BigDecimal
  }

  case class OperationExpr(left: Expression, right: Expression, multiply: Boolean) extends Expression {

    override def name(): String =
      if (multiply)
        s"(${this.left.name()}*${this.right.name()})"
      else
        s"(${this.left.name()}/${this.right.name()})"

    override def multiplicationFactor(): BigDecimal =
      if (multiply)
        this.left.multiplicationFactor() * this.right.multiplicationFactor()
      else
        this.left.multiplicationFactor() / this.right.multiplicationFactor()

  }

  case class UnitExpr(value: Unit) extends Expression {

    override def name(): String = this.value.nameSI
    override def multiplicationFactor(): BigDecimal = this.value.valueSI

  }

  sealed trait Unit {
    def symbol: String
    def quantity: String
    def nameSI: String
    def valueSI: BigDecimal
  }

  case object Second extends Unit {
    override def symbol: String = "s"
    override def quantity: String = "time"
    override def nameSI: String = "s"
    override def valueSI: BigDecimal = 1
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
    override def nameSI: String = "m²"
    override def valueSI: BigDecimal = 10000
  }

  case object Litre extends Unit {
    override def symbol: String = "L"
    override def quantity: String = "volume"
    override def nameSI: String = "m³"
    override def valueSI: BigDecimal = 0.001
  }

  case object Tonne extends Unit {
    override def symbol: String = "t"
    override def quantity: String = "mass"
    override def nameSI: String = "kg"
    override def valueSI: BigDecimal = 1000
  }

  object Unit {

    def fromString(unit: String): Unit = unit.toLowerCase match {
      case "second" | "sec"   => Second
      case "minute" | "min"   => Minute
      case "hour"   | "h"     => Hour
      case "day"    | "d"     => Day
      case "degree" | "°"     => Degree
      case "arcminute" | "'"  => ArcMinute
      case "arcsecond" | "\"" => ArcSecond
      case "hectare"   | "ha" => Hectare
      case "litre" | "l"      => Litre
      case "tonne" | "t"      => Tonne
      case _ => throw new Exception(s"Unmapped unit ($unit).")
    }

  }

}
