package controllers

import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test._
import v1.units.{UnitsController, UnitsControllerComponents}

import scala.concurrent.ExecutionContext

@RunWith(classOf[JUnitRunner])
class UnitsControllerSpec
  extends PlaySpec
    with GuiceOneAppPerSuite
    with Injecting
    with Results {

  private implicit lazy val context: ExecutionContext =
    app.injector.instanceOf(classOf[ExecutionContext])

  private lazy val components: UnitsControllerComponents =
    app.injector.instanceOf(classOf[UnitsControllerComponents])

  private val path: String = "/units/si"
  private val expectedContentType: String = "application/json"

  s"WHEN route $path is called" must {

    def convertTo(controller: UnitsController, variables: (String, String, BigDecimal)): Unit = {
      val request = FakeRequest(GET, s"$path?units=${variables._1}")
      val result = controller.convertToSI.apply(request)
      status(result) mustBe OK
      contentType(result) mustBe Some(expectedContentType)
      contentAsJson(result) mustBe Json.obj("unit_name" -> variables._2, "multiplication_factor" -> variables._3)
    }

    running(fakeApplication()) {
      val controller = new UnitsController(this.components)
      val allUnits = Seq(
        ("(second/second)", "(s/s)", BigDecimal(1)),
        ("(second/s)", "(s/s)", BigDecimal(1)),
        ("(s/second)", "(s/s)", BigDecimal(1)),
        ("(s/s)", "(s/s)", BigDecimal(1)),
        ("(minute/minute)", "(s/s)", BigDecimal(1)),
        ("(minute/min)", "(s/s)", BigDecimal(1)),
        ("(min/minute)", "(s/s)", BigDecimal(1)),
        ("(min/min)", "(s/s)", BigDecimal(1)),
        ("(hour/hour)", "(s/s)", BigDecimal(1)),
        ("(hour/h)", "(s/s)", BigDecimal(1)),
        ("(h/hour)", "(s/s)", BigDecimal(1)),
        ("(h/h)", "(s/s)", BigDecimal(1)),
        ("(degree/minute)", "(rad/s)", BigDecimal("0.00029088820866572")),
        ("(°/min)", "(rad/s)", BigDecimal("0.00029088820866572")),
        ("(degree/min)", "(rad/s)", BigDecimal("0.00029088820866572")),
        ("(°/minute)", "(rad/s)", BigDecimal("0.00029088820866572")),
        ("(arcminute/minute)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(arcminute/min)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("('/minute)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("('/min)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(arcsecond/minute)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(arcsecond/min)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(\"/minute)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(\"/min)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(degree/hour)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(°/h)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(degree/h)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(°/hour)", "(rad/s)", BigDecimal("0.0000048481368110953")),
        ("(arcminute/hour)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(arcminute/h)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("('/hour)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("('/h)", "(rad/s)", BigDecimal("8.0802280184922E-8")),
        ("(arcsecond/hour)", "(rad/s)", BigDecimal("1.3467046697487E-9")),
        ("(arcsecond/h)", "(rad/s)", BigDecimal("1.3467046697487E-9")),
        ("(\"/hour)", "(rad/s)", BigDecimal("1.3467046697487E-9")),
        ("(\"/h)", "(rad/s)", BigDecimal("1.3467046697487E-9")),
        ("(hectare/degree)", "(m²/rad)", BigDecimal("572957.79513082")),
        ("(ha/degree)", "(m²/rad)", BigDecimal("572957.79513082")),
        ("(hectare/°)", "(m²/rad)", BigDecimal("572957.79513082")),
        ("(ha/°)", "(m²/rad)", BigDecimal("572957.79513082")),
        ("(tonne/degree)", "(kg/rad)", BigDecimal("57295.779513082")),
        ("(t/degree)", "(kg/rad)", BigDecimal("57295.779513082")),
        ("(tonne/°)", "(kg/rad)", BigDecimal("57295.779513082")),
        ("(t/°)", "(kg/rad)", BigDecimal("57295.779513082")),
        ("(litre/minute)", "(m³/s)", BigDecimal("0.000016666666666666")),
        ("(L/minute)", "(m³/s)", BigDecimal("0.000016666666666666")),
        ("(litre/min)", "(m³/s)", BigDecimal("0.000016666666666666")),
        ("(L/min)", "(m³/s)", BigDecimal("0.000016666666666666")),
        ("(litre/tonne)", "(m³/kg)", BigDecimal("0.000001")),
        ("(L/tonne)", "(m³/kg)", BigDecimal("0.000001")),
        ("(litre/t)", "(m³/kg)", BigDecimal("0.000001")),
        ("(L/t)", "(m³/kg)", BigDecimal("0.000001")),
        ("(litre*degree/t)", "(m³*(rad/kg))", BigDecimal("1.7453292519943E-8")),
        ("(L*degree/t)", "(m³*(rad/kg))", BigDecimal("1.7453292519943E-8")),
        ("(litre*degree/tonne)", "(m³*(rad/kg))", BigDecimal("1.7453292519943E-8")),
        ("(L*°/t)", "(m³*(rad/kg))", BigDecimal("1.7453292519943E-8")),
        ("(L*°/tonne)", "(m³*(rad/kg))", BigDecimal("1.7453292519943E-8")),
      )

      allUnits.foreach(unit => {
        s"convert ${unit._1} to ${unit._2} THEN return 200 OK" in {
          convertTo(controller, unit)
        }
      })

    }

  }

}
