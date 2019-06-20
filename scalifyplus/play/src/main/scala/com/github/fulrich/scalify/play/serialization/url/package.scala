package com.github.fulrich.scalify.play.serialization

import com.github.fulrich.scalify.play.serialization.url.base.BaseBindings
import com.github.fulrich.scalify.play.serialization.url.installation.InstallationBindings


package object url extends BaseBindings with InstallationBindings {
  val RouteImports: Seq[String] = Seq("com.github.fulrich.scalify.play.bindings._") ++
      BaseBindings.RouteImports ++
      InstallationBindings.RouteImports
}
