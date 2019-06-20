package com.github.fulrich.scalify.play.serialization.url

import play.api.mvc.QueryStringBindable

trait EmptyKeyQueryStringBindable[A] { self: QueryStringBindable[A] =>
  private val emptyKey: String = ""

  def bind(params: Map[String, Seq[String]]): Option[Either[String, A]] = bind(emptyKey, params)
  def unbind(value: A): String = unbind(emptyKey, value)
}
