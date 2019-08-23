// Publishing Information
inThisBuild(List(
  organization := "com.github.fulrich",
  homepage := Some(url("https://github.com/fulrich/scalify")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "fulrich",
      "Fredric Ulrich",
      "9284621+fulrich@users.noreply.github.com",
      url("https://www.fredriculrich.com")
    )
  ),
  resolvers += Resolver.sonatypeRepo("releases"),
  scalaVersion := "2.13.0",
  crossScalaVersions := Seq("2.12.8", "2.13.0"),
  publishArtifact in Test := true
))

// Versions
val Libraries = new {
  val TestChargedVersion = "0.1.16"
  val TestCharged = "com.github.fulrich" %% "test-charged" % TestChargedVersion % Test

  val ScalacticVersion = "3.0.8"
  val Scalactic = "org.scalactic" %% "scalactic" % ScalacticVersion

  val ScalaTestVersion = "3.0.8"
  val ScalaTest = "org.scalatest" %% "scalatest" % ScalaTestVersion % Test

  val PureConfigVersion = "0.11.1"
  val PureConfig = "com.github.pureconfig" %% "pureconfig" % PureConfigVersion
}

val CommonLibraries = Seq(
  Libraries.Scalactic,
  Libraries.PureConfig,
  Libraries.ScalaTest,
  Libraries.TestCharged
)

// Root Project Setup
name := "Scalify"
skip in publish := true

lazy val core = (project in file("core"))
  .settings(
    libraryDependencies ++= CommonLibraries
  )

// Scalify Plus Play Framework
lazy val plusplay = (project in file("plus/play"))
  .settings(
    libraryDependencies ++= CommonLibraries,
  )
  .dependsOn(core % "test->test;compile->compile")

lazy val plussbtplay = (project in file("plus/sbt/play"))
  .settings(
    scalaVersion := "2.12.8",
    crossScalaVersions := Seq("2.12.8")
  )

// Documentation
lazy val documentation = (project in file("docs"))
  .settings(
    scalaVersion := "2.12.8",
    crossScalaVersions := Seq("2.12.8")
  )
