// Scala Versions
lazy val Scala212 = "2.12.8"
lazy val Scala213 = "2.13.0"

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
  scalaVersion := Scala213,
  crossScalaVersions := Seq(Scala212, Scala213),
  publishArtifact in Test := true
))

// Root Project Setup
name := "Scalify"
skip in publish := true

lazy val core = (project in file("core"))
  .settings(
    libraryDependencies ++= Libraries.Common
  )

// Scalify Plus Play Framework
lazy val plusplay = (project in file("plus/play"))
  .settings(
    libraryDependencies ++= Libraries.Common,
  )
  .dependsOn(core % "test->test;compile->compile")

lazy val plussbtplay = (project in file("plus/sbt/play"))
  .settings(
    scalaVersion := Scala212,
    crossScalaVersions := Seq(Scala212)
  )

// Documentation
lazy val documentation = (project in file("docs"))
  .settings(
    scalaVersion := Scala212,
    crossScalaVersions := Seq(Scala212)
  )
