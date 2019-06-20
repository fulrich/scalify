package com.github.fulrich.scalify.serialization.url.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.serialization.url.ObjectUrlBinding
import io.lemonlabs.uri.QueryString
import org.scalactic.Accumulation.withGood


object AuthorizeConfirmationUrlBinding extends ObjectUrlBinding[AuthorizeConfirmation] {
  override def bind(query: QueryString): UrlBind[AuthorizeConfirmation] =
    withGood(
      bind[String](AuthorizeConfirmation.ShopKey, query),
      bind[String](AuthorizeConfirmation.AuthorizationCodeKey, query),
      bind[String](AuthorizeConfirmation.NonceKey, query),
      bind[Instant](AuthorizeConfirmation.TimestampKey, query)
    )(AuthorizeConfirmation.apply)

  override def unbind(parameters: AuthorizeConfirmation): QueryString = unbindList(
    unbind(AuthorizeConfirmation.ShopKey, parameters.shop),
    unbind(AuthorizeConfirmation.AuthorizationCodeKey, parameters.authorizationCode),
    unbind(AuthorizeConfirmation.NonceKey, parameters.nonce),
    unbind(AuthorizeConfirmation.TimestampKey, parameters.timestamp)
  )
}
