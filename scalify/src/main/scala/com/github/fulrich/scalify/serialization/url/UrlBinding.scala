package com.github.fulrich.scalify.serialization.url

import java.time.Instant

import com.github.fulrich.scalify.ScalifyError
import io.lemonlabs.uri.{QueryString, Url}
import org.scalactic.{Bad, Every, One, Or}


trait UrlBinding[A] {
  val missingMessage = "Required key was not found."
  def missingError(field: Option[String]): A Or Every[ScalifyError] = Bad(One(ScalifyError(missingMessage, field)))


  def bind(key: String, query: QueryString): Option[UrlBind[A]]

  def bind(key: String, uri: String): Option[UrlBind[A]] =
    bind(key, Url.parse(uri).query)

  def bind(key: String, queryMap: Map[String, Seq[String]]): Option[UrlBind[A]] = {
    val queryMapList = queryMap.toVector.flatMap { case (paramKey, paramList) =>
      paramList.map {
        paramValue => (paramKey, Option(paramValue))
      }
    }

    bind(key, QueryString(queryMapList))
  }

  def requiredBind(key: String, uri: String): UrlBind[A] =
    bind(key, uri).getOrElse(missingError(Some(key)))

  def requiredBind(key: String, queryMap: Map[String, Seq[String]]): UrlBind[A] =
    bind(key, queryMap).getOrElse(missingError(Some(key)))

  def requiredBind(key: String, query: QueryString): UrlBind[A] =
    bind(key, query).getOrElse(missingError(Some(key)))


  def unbind(key: String, value: A): String = s"$key=$value"
}

object UrlBinding {
  implicit val stringUrlBinding: UrlBinding[String] = StringBinding
  implicit val instantUrlBinding: UrlBinding[Instant] = InstantBinding
}
