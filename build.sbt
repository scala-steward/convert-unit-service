import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

// Project configurations using scala classes at /project folder.
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(ConvertUnitService, Dependencies)

scalaVersion := "2.13.8"

lazy val conf: Config = ConfigFactory.parseFile(new File("conf/application.conf"))

// Docker Configuration
import com.typesafe.sbt.packager.docker.DockerChmodType
import com.typesafe.sbt.packager.docker.DockerPermissionStrategy
dockerChmodType := DockerChmodType.UserGroupWriteExecute
dockerPermissionStrategy := DockerPermissionStrategy.CopyChown

Docker / maintainer := "felipebonezi@gmail.com"
Docker / packageName := conf.getString("play.app.name")
Docker / version := conf.getString("play.app.version")
dockerExposedPorts := Seq(9000)
dockerUpdateLatest := true
