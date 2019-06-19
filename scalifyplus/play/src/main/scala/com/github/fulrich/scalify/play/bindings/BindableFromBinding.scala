package com.github.fulrich.scalify.play.bindings

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.scalify.serialization.url.UrlBinding
import org.scalactic.Every
import play.api.mvc.QueryStringBindable


abstract class BindableFromBinding[A](binding: UrlBinding[A]) extends QueryStringBindable[A] {
  private val errorDelimiter: String = "<br>"

  override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, A]] =
    binding.bind(key, params).map { boundValue =>
      boundValue.badMap(errors => scalifyErrorsToString(key, errors)).toEither
    }

  override def unbind(key: String, value: A): String = binding.unbind(key, value)


  private def scalifyErrorsToString(key: String, errors: Every[ScalifyError]): String =
    errors
      .map(error => scalifyErrorToString(key, error))
      .mkString(errorDelimiter)

  private def scalifyErrorToString(key: String, error: ScalifyError): String =
    s"${error.field.getOrElse(key)}: ${error.message}"
}
