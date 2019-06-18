package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.bindings.{InstantBinding, StringBinding}
import com.github.fulrich.scalify.play.hmac.HmacRequest
import org.scalactic.Accumulation._
import org.scalactic._


object InstallParametersBinding {
  def bind(request: HmacRequest[_]): InstallParameters Or Every[String] =
    withGood(
      StringBinding.bind(InstallParameters.ShopKey, request),
      InstantBinding.bind(InstallParameters.TimestampKey, request)
    )(InstallParameters.apply)

  def unbind(parameters: InstallParameters): String =
    Vector(
      StringBinding.unbind(InstallParameters.ShopKey, parameters.shop),
      InstantBinding.unbind(InstallParameters.TimestampKey, parameters.timestamp)
    ).sorted.mkString("&")
}
