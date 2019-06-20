package com.github.fulrich.scalify.serialization.url

import io.lemonlabs.uri.QueryString


trait UrlBindingDsl extends UrlBindingTypes {
  def optionalBind[A](key: String, uri: String)(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.optionalBind(key, uri)

  def optionalBind[A](key: String, query: QueryString)(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.optionalBind(key, query)

  def optionalBind[A](key: String, queryMap: QueryMap)(implicit binding: UrlBinding[A]): Option[UrlBind[A]] =
    binding.optionalBind(key, queryMap)

  def bind[A](key: String, uri: String)(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.bind(key, uri)

  def bind[A](key: String, queryMap: QueryMap)(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.bind(key, queryMap)

  def bind[A](key: String, query: QueryString)(implicit binding: UrlBinding[A]): UrlBind[A] =
    binding.bind(key, query)

  def unbind[A](key: String, value: A)(implicit binding: UrlBinding[A]): QueryString =
    binding.unbind(key, value)


  def bind[A](query: QueryString)(implicit binding: ObjectUrlBinding[A]): UrlBind[A] = binding.bind(query)

  def bind[A](uri: String)(implicit binding: ObjectUrlBinding[A]): UrlBind[A] = binding.bind(uri)

  def bind[A](queryMap: QueryMap)(implicit binding: ObjectUrlBinding[A]): UrlBind[A] = binding.bind(queryMap)

  def unbind[A](value: A)(implicit binding: ObjectUrlBinding[A]): QueryString = binding.unbind(value)
}
