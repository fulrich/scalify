ThisBuild / organization := "com.github.fulrich"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / crossScalaVersions := Seq("2.11.12", "2.12.8")


lazy val scalify = project in file("scalify")

lazy val scalifyplusplay = (project in file("scalifyplus/play"))
  .dependsOn(scalify, scalify % "test->test")
