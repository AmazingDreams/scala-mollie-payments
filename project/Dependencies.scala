import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  lazy val mockito = "org.mockito" % "mockito-core" % "2.13.0"
  lazy val scalajHttp = "org.scalaj" %% "scalaj-http" % "2.3.0"
  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.6.7"
}
