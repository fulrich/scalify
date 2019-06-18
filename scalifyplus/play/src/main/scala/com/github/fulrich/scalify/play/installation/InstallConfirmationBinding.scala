package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.bindings.{InstantBinding, StringBinding}
import com.github.fulrich.scalify.play.hmac.HmacRequest
import org.scalactic.Accumulation.withGood
import org.scalactic.{Every, Or}


object InstallConfirmationBinding {
  def bind(request: HmacRequest[_]): AuthorizeConfirmation Or Every[String] =
    withGood(
      StringBinding.bind(AuthorizeConfirmation.ShopKey, request),
      StringBinding.bind(AuthorizeConfirmation.AuthorizationCodeKey, request),
      StringBinding.bind(AuthorizeConfirmation.NonceKey, request),
      InstantBinding.bind(AuthorizeConfirmation.TimestampKey, request)
    )(AuthorizeConfirmation.apply)

  def unbind(confirmation: AuthorizeConfirmation): String =
    Vector(
      StringBinding.unbind(AuthorizeConfirmation.ShopKey, confirmation.shop),
      StringBinding.unbind(AuthorizeConfirmation.AuthorizationCodeKey, confirmation.authorizationCode),
      StringBinding.unbind(AuthorizeConfirmation.NonceKey, confirmation.nonce),
      InstantBinding.unbind(AuthorizeConfirmation.TimestampKey, confirmation.timestamp)
    ).sorted.mkString("&")
}
