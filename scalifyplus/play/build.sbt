name := """scalifyplus-play"""

// Production Dependencies
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.7.1"  % Provided,
  "com.github.pureconfig" %% "pureconfig" % "0.11.0",
)

// Test Dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5"  % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test,
  "com.typesafe.play" %% "play-test" % "2.7.1"  % Test,
  "com.github.fulrich" %% "test-charged" % "0.1.12" % Test
)