package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallConfirmation
import com.github.fulrich.scalify.play.bindings.{InstantBinding, StringBinding}
import com.github.fulrich.scalify.play.hmac.HmacRequest
import org.scalactic.Accumulation.withGood
import org.scalactic.{Every, Or}


object InstallConfirmationBinding {
  def bind(request: HmacRequest[_]): InstallConfirmation Or Every[String] =
    withGood(
      StringBinding.bind(InstallConfirmation.ShopKey, request),
      StringBinding.bind(InstallConfirmation.AuthorizationCodeKey, request),
      StringBinding.bind(InstallConfirmation.NonceKey, request),
      InstantBinding.bind(InstallConfirmation.TimestampKey, request)
    )(InstallConfirmation.apply)

  def unbind(confirmation: InstallConfirmation): String =
    Vector(
      StringBinding.unbind(InstallConfirmation.ShopKey, confirmation.shop),
      StringBinding.unbind(InstallConfirmation.AuthorizationCodeKey, confirmation.authorizationCode),
      StringBinding.unbind(InstallConfirmation.NonceKey, confirmation.nonce),
      InstantBinding.unbind(InstallConfirmation.TimestampKey, confirmation.timestamp)
    ).sorted.mkString("&")
}
