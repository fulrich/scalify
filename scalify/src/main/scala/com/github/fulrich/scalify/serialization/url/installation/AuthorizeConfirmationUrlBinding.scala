package com.github.fulrich.scalify.serialization.url.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.serialization.url.{UrlBind, UrlBinding, UrlBindingDsl}
import io.lemonlabs.uri.QueryString
import org.scalactic.Accumulation.withGood


object AuthorizeConfirmationUrlBinding extends UrlBinding[AuthorizeConfirmation] with UrlBindingDsl {
  override def bind(key: String, query: QueryString): Option[UrlBind[AuthorizeConfirmation]] =
    Option(withGood(
      requiredBind[String](AuthorizeConfirmation.ShopKey, query),
      requiredBind[String](AuthorizeConfirmation.AuthorizationCodeKey, query),
      requiredBind[String](AuthorizeConfirmation.NonceKey, query),
      requiredBind[Instant](AuthorizeConfirmation.TimestampKey, query)
    )(AuthorizeConfirmation.apply))

  override def unbind(key: String, parameters: AuthorizeConfirmation): String =
    Vector(
      unbind(AuthorizeConfirmation.ShopKey, parameters.shop),
      unbind(AuthorizeConfirmation.AuthorizationCodeKey, parameters.authorizationCode),
      unbind(AuthorizeConfirmation.NonceKey, parameters.nonce),
      unbind(AuthorizeConfirmation.TimestampKey, parameters.timestamp)
    ).sorted.mkString("&")
}
