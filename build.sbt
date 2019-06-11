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
  scalaVersion := "2.12.8",
  crossScalaVersions := List("2.11.12", "2.12.8"),
))

// Root Project Setup
name := "ScalifyRoot"
skip in publish := true


// Core Scalify
lazy val scalify = project in file("scalify")

// Subprojects
lazy val scalifyplusplay = (project in file("scalifyplus/play"))
  .dependsOn(scalify % "test->test;compile->compile")
