package com.github.fulrich.scalify.play.bindings

import com.github.fulrich.scalify.ScalifyError
import org.scalactic.Every


object ScalifyErrorsToString {
  private val ErrorDelimiter: String = "<br>"


  def apply(key: String, errors: Every[ScalifyError]): String =
    errors
      .map(error => apply(key, error))
      .mkString(ErrorDelimiter)

  def apply(key: String, error: ScalifyError): String =
    s"${error.field.getOrElse(key)}: ${error.message}"
}
