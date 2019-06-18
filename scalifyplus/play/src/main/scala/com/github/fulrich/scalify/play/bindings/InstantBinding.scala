package com.github.fulrich.scalify.play.bindings

import java.time.Instant

import org.scalactic._
import play.api.mvc.Request

import scala.util.{Failure, Success, Try}


object InstantBinding {
  def missingInstantMessage(key: String) = s"An Instant is required for key: $key."
  def invalidTimestampMessage(key: String) = s"Could not parse key $key to an Instant."

  def bind[A](key: String, request: Request[A]): Instant Or Every[String] =
    request.getQueryString(key) match {
      case Some(timestampString) => parseInstant(key, timestampString)
      case None => Bad(One(missingInstantMessage(key)))
    }

  def parseInstant(key: String, timestampString: String): Instant Or Every[String] =
    Try(Instant.ofEpochSecond(timestampString.toLong)) match {
      case Success(timestampInstant) => Good(timestampInstant)
      case Failure(_) => Bad(One(invalidTimestampMessage(key)))
    }

  def unbind(key: String, instant: Instant): String = s"$key=${instant.getEpochSecond}"
}

