package com.github.fulrich.scalify.play.serialization.url.base

import java.time.Instant

import play.api.mvc.QueryStringBindable


trait  BaseBindings {
  implicit val instantBindable: QueryStringBindable[Instant] = InstantBindable
}

object BaseBindings {
  val RouteImports: Seq[String] = Seq(
    classOf[Instant].getCanonicalName
  )
}
