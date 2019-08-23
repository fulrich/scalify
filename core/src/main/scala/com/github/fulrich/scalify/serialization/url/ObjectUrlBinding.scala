package com.github.fulrich.scalify.serialization.url

import io.lemonlabs.uri.{QueryString, Url}


trait ObjectUrlBinding[A] extends UrlBindingDsl {
  def bind(uri: String): UrlBind[A] = bind(Url.parse(uri).query)

  def bind(queryMap: Map[String, Seq[String]]): UrlBind[A] = bind(QueryStringFromMap(queryMap))

  def bind(query: QueryString): UrlBind[A]


  def unbind(value: A): QueryString

  def unbindList(queries: QueryString*): QueryString = queries.reduce[QueryString] { case (left, right) => left.addParams(right) }
}
