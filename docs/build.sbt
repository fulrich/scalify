name := """documentation"""
publishArtifact := false
scalaVersion := "2.12.8"

enablePlugins(MicrositesPlugin)

micrositeName := "Scalify"
micrositeDescription := "Build Shopify Apps using Scala"
micrositeBaseUrl := "/scalify"
micrositeDocumentationUrl := "/scalify/docs"
micrositeAuthor := "fulrich"
micrositeHomepage := "https://fulrich.github.io/scalify/"
micrositeGithubOwner := "fulrich"
micrositeGithubRepo := "scalify"
micrositeHighlightTheme := "darcula"
micrositeGithubToken := sys.env.get("GITHUB_TOKEN")
micrositePushSiteWith := GitHub4s
micrositeGitterChannel := false
micrositeShareOnSocial := false
micrositeFooterText := None
