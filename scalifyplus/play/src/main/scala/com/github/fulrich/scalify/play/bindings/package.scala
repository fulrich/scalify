package com.github.fulrich.scalify.play

import java.time.Instant

import com.github.fulrich.scalify.play.bindings.installation.InstallationBindings
import play.api.mvc.QueryStringBindable


package object bindings extends InstallationBindings {
  val routeImports: Seq[String] =
    Seq("com.github.fulrich.scalify.play.bindings._") ++
    InstallationBindings.RouteImports

  implicit val timestampBindable: QueryStringBindable[Instant] = InstantBindable
}
