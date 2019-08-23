lazy val micrositeSettings = Seq(
  micrositeName := "Scalify",
  micrositeDescription := "Build Shopify Apps using Scala",
  micrositeBaseUrl := "/scalify",
  micrositeDocumentationUrl := "/scalify-charged/docs",
  micrositeAuthor := "fulrich",
  micrositeHomepage := "https://fulrich.github.io/scalify/",
  micrositeGithubOwner := "fulrich",
  micrositeGithubRepo := "scalify",
  micrositeHighlightTheme := "darcula",
  micrositeGithubToken := sys.env.get("GITHUB_TOKEN"),
  micrositePushSiteWith := GitHub4s,
  micrositeGitterChannel := false,
  micrositeShareOnSocial := false,
  micrositeFooterText := None
)

lazy val docs = (project in file("docs"))
  .enablePlugins(MicrositesPlugin)
  .settings(
    publishArtifact := false,
    scalaVersion := "2.12.8",
    crossScalaVersions := Seq("2.12.8")
  )
  .settings(micrositeSettings)