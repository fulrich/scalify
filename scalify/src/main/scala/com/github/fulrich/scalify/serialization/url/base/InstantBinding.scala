package com.github.fulrich.scalify.serialization.url.base

import java.time.Instant

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.scalify.serialization.url.UrlBinding
import io.lemonlabs.uri.QueryString
import org.scalactic._

import scala.util.{Failure, Success, Try}

object InstantBinding extends UrlBinding[Instant] {
  override val missingMessage = "An Instant is required."
  def invalidTimestamp(key: String) = Bad(One(ScalifyError("Unable to parse as an Instant.", Some(key))))

  override def bind(key: String, query: QueryString): Option[Instant Or Every[ScalifyError]] =
    query.param(key).map { value =>
      parseInstant(key, value)
    }

  private def parseInstant(key: String, instantString: String): Instant Or Every[ScalifyError] =
    Try(Instant.ofEpochSecond(instantString.toLong)) match {
      case Success(timestampInstant) => Good(timestampInstant)
      case Failure(_) => invalidTimestamp(key)
    }

  override def unbind(key: String, instant: Instant): String = s"$key=${instant.getEpochSecond}"
}
