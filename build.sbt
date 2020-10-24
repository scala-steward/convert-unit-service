// Project configurations using scala classes at /project folder.
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(ConvertUnitService, Dependencies)

scalaVersion := "2.13.3"
