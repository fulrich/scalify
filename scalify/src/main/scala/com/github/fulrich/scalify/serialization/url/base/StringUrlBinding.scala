package com.github.fulrich.scalify.serialization.url.base

import com.github.fulrich.scalify.serialization.url.UrlBinding
import io.lemonlabs.uri.QueryString
import org.scalactic.Good


object StringUrlBinding extends UrlBinding[String] {
  override val missingMessage = "A String value is required."

  override def optionalBind(key: String, query: QueryString): Option[UrlBind[String]] =
    query.param(key).map(value => Good(value))
}
