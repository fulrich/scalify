package com.github.fulrich.scalify.play.bindings

import play.api.mvc.QueryStringBindable

trait EmptyKeyQueryStringBindable[A] extends QueryStringBindable[A] {
  private val emptyKey: String = ""

  def bind(params: Map[String, Seq[String]]): Option[Either[String, A]] = bind(emptyKey, params)
  def unbind(value: A): String = unbind(emptyKey, value)
}
