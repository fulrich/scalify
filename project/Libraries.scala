import sbt._

object Libraries {
  val Common: Seq[ModuleID] = Seq(
    Scalactic,
    PureConfig,
    ScalaTest,
    TestCharged
  )

  lazy val TestChargedVersion = "0.1.16"
  lazy val TestCharged = "com.github.fulrich" %% "test-charged" % TestChargedVersion % Test

  lazy val ScalacticVersion = "3.0.8"
  lazy val Scalactic = "org.scalactic" %% "scalactic" % ScalacticVersion

  lazy val ScalaTestVersion = "3.0.8"
  lazy val ScalaTest = "org.scalatest" %% "scalatest" % ScalaTestVersion % Test

  lazy val PureConfigVersion = "0.11.1"
  lazy val PureConfig = "com.github.pureconfig" %% "pureconfig" % PureConfigVersion
}
