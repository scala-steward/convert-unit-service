import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.sbt.SbtNativePackager.Universal
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object ConvertUnitService extends AutoPlugin {
  private lazy val conf: Config = ConfigFactory.parseFile(new File("conf/application.conf"))

  override def trigger = allRequirements
  override def requires: sbt.Plugins = JvmPlugin

  override def projectSettings = Seq(
    organization := "br.com.felipebonezi",
    name := conf.getString("play.app.name"),
    version := conf.getString("play.app.version"),
    resolvers += Resolver.typesafeRepo("releases"),
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    javaOptions in Universal ++= Seq("-Dpidfile.path=/dev/null"),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8",
      "-target:jvm-1.8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos"),
    autoAPIMappings := true
  )
}
