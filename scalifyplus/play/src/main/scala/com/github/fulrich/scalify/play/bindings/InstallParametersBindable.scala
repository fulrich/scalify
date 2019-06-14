package com.github.fulrich.scalify.play.bindings

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import play.api.mvc.QueryStringBindable


object InstallParametersBindable extends EmptyKeyQueryStringBindable[InstallParameters] {
  val ShopKey = "shop"
  val TimestampKey = "timestamp"

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
