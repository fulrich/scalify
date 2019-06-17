package com.github.fulrich.scalify.play.bindings.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.installation.InstallParameters.{ShopKey, TimestampKey}
import com.github.fulrich.scalify.play.bindings.{EmptyKeyQueryStringBindable, InstantBindable}
import play.api.mvc.QueryStringBindable

object InstallParametersBindable extends EmptyKeyQueryStringBindable[InstallParameters] {
  val stringBinder: QueryStringBindable[String] = implicitly[QueryStringBindable[String]]
  val instantBinder: QueryStringBindable[Instant] = InstantBindable


  override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, InstallParameters]] = {
    for {
      boundShop <- stringBinder.bind(ShopKey, params)
      boundTimestamp <- instantBinder.bind(TimestampKey, params)
    } yield {
      (boundShop, boundTimestamp) match {
        case (Right(shop), Right(timestamp)) => Right(InstallParameters(shop, timestamp))
        case _ => Left("Unable to bind an Install Parameters")
      }
    }
  }

  override def unbind(key: String, value: InstallParameters): String =
    stringBinder.unbind(ShopKey, value.shop) + "&" + instantBinder.unbind(TimestampKey, value.timestamp)
}
