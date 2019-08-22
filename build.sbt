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
  scalaVersion := "2.12.8",
  crossScalaVersions := Seq("2.11.12", "2.12.8"),
  publishArtifact in Test := true
))

// Versions
val Libraries = new {
  val TestChargedVersion = "0.1.12"
  val TestCharged = "com.github.fulrich" %% "test-charged" % TestChargedVersion % Test

  val ScalacticVersion = "3.0.8"
  val Scalactic = "org.scalactic" %% "scalactic" % ScalacticVersion

  val ScalaTestVersion = "3.0.5"
  val ScalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test
}

// Root Project Setup
name := "ScalifyRoot"
skip in publish := true


// Core Scalify
lazy val scalify = (project in file("scalify"))
  .settings(
    libraryDependencies ++= Seq(
      Libraries.Scalactic,
      Libraries.ScalaTest,
      Libraries.TestCharged
    )
  )

// Subprojects
lazy val scalifyplusplay = (project in file("scalifyplus/play"))
  .settings(
    libraryDependencies ++= Seq(
      Libraries.Scalactic,
      Libraries.ScalaTest,
      Libraries.TestCharged
    ),
    excludeDependencies ++= Seq(
      ExclusionRule("com.github.fulrich", "scalify")
    )
  )
  .dependsOn(scalify % "test->test;compile->compile")
