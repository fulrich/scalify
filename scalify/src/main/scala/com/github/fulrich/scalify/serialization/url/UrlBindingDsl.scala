package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.scalify.ScalifyError
import io.lemonlabs.uri.QueryString
import org.scalactic.{Every, Or}


trait UrlBindingDsl {
  type UrlBind[A] = A Or Every[ScalifyError]

  def bind[A](key: String, uri: String)(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.bind(key, uri)

  def bind[A](key: String, query: QueryString)(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.bind(key, query)

  def bind[A](key: String, queryMap: Map[String, Seq[String]])(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.bind(key, queryMap)


  def requiredBind[A](key: String, uri: String)(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.requiredBind(key, uri)

  def requiredBind[A](key: String, queryMap: Map[String, Seq[String]])(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.requiredBind(key, queryMap)

  def requiredBind[A](key: String, query: QueryString)(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.requiredBind(key, query)


  def unbind[A](key: String, value: A)(implicit binding: UrlBinding[A]): String = binding.unbind(key, value)
}
