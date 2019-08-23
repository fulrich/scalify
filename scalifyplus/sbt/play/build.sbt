name := """scalifyplus-sbt-play"""

enablePlugins(SbtPlugin)
sbtPlugin := true
scalaVersion := "2.12.8"
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}
scriptedBufferLog := false
