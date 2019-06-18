name := """scalifyplus-play"""

// Library Versions
val Versions = new {
  val Play = new {
    val Core = "2.7.1"
    val ScalaTest = "4.0.2"
  }
}

// Production Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % Versions.Play.Core  % Provided,
  "com.github.pureconfig" %% "pureconfig" % "0.11.0",
)

// Test Dependencies
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % Versions.Play.ScalaTest % Test,
  "com.typesafe.play" %% "play-test" % Versions.Play.Core % Test
)
