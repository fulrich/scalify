package com.github.fulrich.scalify.serialization.url.base

import java.time.Instant

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.scalify.serialization.url.UrlBinding
import io.lemonlabs.uri.QueryString
import org.scalactic._

import scala.util.{Failure, Success, Try}


object InstantUrlBinding extends UrlBinding[Instant] {
  override val missingMessage = "An Instant is required."
  def invalidTimestamp(key: String) = Bad(One(ScalifyError("Unable to parse as an Instant.", Some(key))))


  override def optionalBind(key: String, query: QueryString): Option[UrlBind[Instant]] =
    query.param(key).map { parameter =>
      Try(Instant.ofEpochSecond(parameter.toLong)) match {
        case Success(timestampInstant) => Good(timestampInstant)
        case Failure(_) => invalidTimestamp(key)
      }
    }

  override def unbind(key: String, instant: Instant): QueryString =
    QueryString.fromPairs(key -> instant.getEpochSecond.toString)
}
