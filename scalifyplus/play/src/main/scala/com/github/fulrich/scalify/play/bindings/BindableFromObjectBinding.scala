package com.github.fulrich.scalify.play.bindings

import com.github.fulrich.scalify.serialization.url.ObjectUrlBinding
import play.api.mvc.QueryStringBindable


abstract class BindableFromObjectBinding[A](binding: ObjectUrlBinding[A]) extends QueryStringBindable[A] {
  override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, A]] =
    Option(
      binding
        .bind(params)
        .badMap(errors => ScalifyErrorsToString(key, errors))
        .toEither
    )

  override def unbind(key: String, value: A): String = binding.unbind(value).toString.drop(1)
}
