package com.github.fulrich.scalify.play.serialization

import java.time.Instant

import com.github.fulrich.scalify.play.serialization.url.installation.InstallationBindings
import play.api.mvc.QueryStringBindable


package object url extends InstallationBindings {
  val routeImports: Seq[String] =
    Seq("com.github.fulrich.scalify.play.bindings._") ++
      InstallationBindings.RouteImports

  implicit val timestampBindable: QueryStringBindable[Instant] = InstantBindable
}
