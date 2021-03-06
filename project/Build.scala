import sbt._
import Keys._

object Build extends Build {
  import AspectJ._
  import NewRelic._
  import Settings._
  import Dependencies._

  lazy val root = Project("root", file("."))
    .aggregate(kamonCore, kamonUow, kamonDashboard)
    .settings(basicSettings: _*)

  lazy val kamonCore = Project("kamon-core", file("kamon-core"))
    .settings(basicSettings: _*)
    .settings(revolverSettings: _*)
    .settings(aspectJSettings: _*)
    .settings(newrelicSettings: _*)

    .settings(
      libraryDependencies ++=
        compile(akkaActor, akkaAgent, sprayCan, sprayClient, sprayRouting, sprayServlet, aspectJ, metrics, sprayJson) ++
        test(scalatest, akkaTestKit, sprayTestkit))
    //.dependsOn(kamonDashboard)

  lazy val kamonUow = Project("kamon-uow", file("kamon-uow"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= compile(akkaActor, akkaSlf4j, sprayRouting))
    .dependsOn(kamonCore)

  lazy val kamonDashboard = Project("kamon-dashboard", file("kamon-dashboard"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= compile(akkaActor, akkaSlf4j, sprayRouting, sprayCan, sprayJson))
    .dependsOn(kamonCore)
}