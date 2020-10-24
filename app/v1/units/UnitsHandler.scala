package v1.units

import java.math.{MathContext, RoundingMode}

import javax.inject.Inject
import v1.units.domain.Entities._
import v1.units.domain.Response.ConvertResponse

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class UnitsHandler @Inject()()(
  implicit ec: ExecutionContext) {

  private lazy val significantContext = new MathContext(14, RoundingMode.DOWN)

  def convertToSI(unit: String): Future[ConvertResponse] =
    Future.successful(makeExpression(unit))
      .map(expr => ConvertResponse(expr.name(), BigDecimal(expr.multiplicationFactor().toString(), significantContext)))

  private def makeExpression(unit: String): Expression = {
    val c1 = unit.count(p => p == '(')
    val c2 = unit.count(p => p == ')')
    if (c1 != c2)
      throw new Exception("Invalid unit expression.")

    val formula = s"${unit.replaceAll(" ", "")}"

    val queue = mutable.ArrayDeque[String]()
    val builder = new StringBuilder()
    formula.foreach(char => {
      if (char == '(')
        queue.addOne(char.toString)
      else if (char == ')')
        queue.removeFirst(_ == "(")
      else if (char == '*' || char == '/') {
        queue.addOne(builder.toString())
        builder.clear()
        queue.addOne(char.toString)
      } else
        builder.append(char)
    })
    queue.addOne(builder.toString())
    builder.clear()

    if (queue.contains("(") || queue.length % 2 == 0)
      throw new Exception("Invalid unit expression.")

    getExpression(queue.mkString(""))
  }

  private def getExpression(unit: String): Expression = {
    val formula =
      if (unit.startsWith("(")) {
        val firstIdx = unit.indexOf("(")
        val lastIdx = unit.lastIndexOf(")")
        unit.substring(firstIdx + 1, lastIdx)
      } else {
        unit
      }

    val mIdx = formula.indexOf("*")
    val dIdx = formula.indexOf("/")
    if (mIdx >= 0 || dIdx >= 0) {
      val multiply = (mIdx != -1 && mIdx < dIdx) || dIdx == -1
      val left = formula.substring(0, if (multiply) mIdx else dIdx)
      val right = formula.substring(if (multiply) mIdx + 1 else dIdx + 1)

      val leftUnit = UnitExpr(Unit.fromString(left))
      val rightUnit = getExpression(right)
      OperationExpr(leftUnit, rightUnit, multiply)
    } else {
      UnitExpr(Unit.fromString(formula))
    }
  }

}
