import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.amazingdreams",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-mollie-payments",
    libraryDependencies ++= Seq(
      playJson,
      scalajHttp,
      scalaTest % Test,
      mockito % Test
    )
  )

homepage := Some(url("https://github.com/AmazingDreams/scala-mollie-payments"))
scmInfo := Some(ScmInfo(url("https://github.com/AmazingDreams/scala-mollie-payments"),
                            "git@github.com:AmazingDreams/scala-mollie-payments.git"))
licenses += ("MIT", url("https://opensource.org/licenses/MIT"))
publishMavenStyle := true

publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)