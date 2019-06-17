package com.github.fulrich.scalify.play.bindings.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallConfirmation
import com.github.fulrich.scalify.installation.InstallConfirmation.{AuthorizationCodeKey, NonceKey, ShopKey, TimestampKey}
import com.github.fulrich.scalify.play.bindings.{EmptyKeyQueryStringBindable, InstantBindable}
import play.api.mvc.QueryStringBindable

object InstallConfirmationBindable extends EmptyKeyQueryStringBindable[InstallConfirmation] {
  val stringBinder: QueryStringBindable[String] = implicitly[QueryStringBindable[String]]
  val instantBinder: QueryStringBindable[Instant] = InstantBindable


  override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, InstallConfirmation]] = {
    for {
      boundShop <- stringBinder.bind(ShopKey, params)
      boundCode <- stringBinder.bind(AuthorizationCodeKey, params)
      boundNonce <- stringBinder.bind(NonceKey, params)
      boundTimestamp <- instantBinder.bind(TimestampKey, params)
    } yield {
      (boundShop, boundCode, boundNonce, boundTimestamp) match {
        case (Right(shop), Right(code), Right(nonce), Right(timestamp)) => Right(InstallConfirmation(shop, code, nonce, timestamp))
        case _ => Left("Unable to bind an Install Parameters")
      }
    }
  }

  override def unbind(key: String, value: InstallConfirmation): String = Seq(
    stringBinder.unbind(ShopKey, value.shop),
    stringBinder.unbind(AuthorizationCodeKey, value.authorizationCode),
    stringBinder.unbind(NonceKey, value.nonce),
    instantBinder.unbind(TimestampKey, value.timestamp)
  ).mkString("&")
}
