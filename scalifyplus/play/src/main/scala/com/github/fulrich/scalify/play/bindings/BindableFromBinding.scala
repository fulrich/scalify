package com.github.fulrich.scalify.play.bindings

import com.github.fulrich.scalify.serialization.url.UrlBinding
import play.api.mvc.QueryStringBindable


abstract class BindableFromBinding[A](binding: UrlBinding[A]) extends QueryStringBindable[A] {
  override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, A]] =
    binding.optionalBind(key, params).map { boundValue =>
      boundValue.badMap(errors => ScalifyErrorsToString(key, errors)).toEither
    }

  override def unbind(key: String, value: A): String = binding.unbind(key, value)
}
