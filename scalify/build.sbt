name := """scalify"""

// Production Dependencies
libraryDependencies ++= Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.11.0",
  "io.lemonlabs" %% "scala-uri" % "1.4.5"
)

// Test Dependencies
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5"  % Test,
  "com.github.fulrich" %% "test-charged" % "0.1.12" % Test
)
