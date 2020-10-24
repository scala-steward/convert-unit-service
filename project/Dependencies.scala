import play.sbt.PlayImport._
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object Dependencies extends AutoPlugin {

  override def trigger = allRequirements
  override def requires: sbt.Plugins = JvmPlugin

  override def projectSettings = Seq(
    libraryDependencies ++= Seq(
      guice,
      ws,
      "net.logstash.logback" % "logstash-logback-encoder" % "6.2",
      "io.lemonlabs" %% "scala-uri" % "1.5.1",
      "net.codingwell" %% "scala-guice" % "4.2.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    )
  )
}
