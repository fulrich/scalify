package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.scalify.ScalifyError
import io.lemonlabs.uri.QueryString
import org.scalactic.{Every, Good, Or}

object StringBinding extends UrlBinding[String] {
  override val missingMessage = "A String value is required."

  override def bind(key: String, query: QueryString): Option[String Or Every[ScalifyError]] =
    query.param(key).map(value => Good(value))
}
