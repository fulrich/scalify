package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.scalify.ScalifyError
import io.lemonlabs.uri.{QueryString, Url}
import org.scalactic.{Bad, One}


trait UrlBinding[A] extends UrlBindingTypes {
  val missingMessage = "Required key was not found."
  def missingError(key: String): UrlBind[A] = Bad(One(ScalifyError(missingMessage, Some(key))))


  def optionalBind(key: String, query: QueryString): Option[UrlBind[A]]

  def optionalBind(key: String, uri: String): Option[UrlBind[A]] = optionalBind(key, Url.parse(uri).query)

  def optionalBind(key: String, queryMap: QueryMap): Option[UrlBind[A]] = optionalBind(key, QueryStringFromMap(queryMap))


  def bind(key: String, uri: String): UrlBind[A] = bind(key, Url.parse(uri).query)

  def bind(key: String, queryMap: QueryMap): UrlBind[A] = bind(key, QueryStringFromMap(queryMap))

  def bind(key: String, query: QueryString): UrlBind[A] = optionalBind(key, query).getOrElse(missingError(key))


  def unbind(key: String, value: A): String = unbind(key -> value.toString)

  protected def unbind(keyValuePair: (String, String)): String = s"${keyValuePair._1}=${keyValuePair._2}"
}
