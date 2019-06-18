package com.github.fulrich.scalify.play.bindings

import org.scalactic._
import play.api.mvc.Request


object StringBinding {
  def missingStringMessage(key: String) = s"A String is required for key: $key."

  def bind[A](key: String, request: Request[A]): String Or Every[String] =
    request.getQueryString(key) match {
      case Some(parsedString) => Good(parsedString)
      case None => Bad(One(missingStringMessage(key)))
    }

  def unbind(key: String, value: String): String = s"$key=$value"
}
