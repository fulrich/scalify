package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.bindings.InstantBinding
import com.github.fulrich.scalify.play.hmac.HmacRequest
import org.scalactic.Accumulation._
import org.scalactic._


object InstallParametersBinding {
  val MissingShopMessage = "A valid shop must be provided."
  val MissingTimestampMessage = "A valid timestamp must be provided."
  val InvalidTimestampMessage = s"Could not parse key ${InstallParameters.TimestampKey} to an Instant."

  def bind(request: HmacRequest[_]): InstallParameters Or Every[String] =
    withGood(parseShop(request), InstantBinding.bind(InstallParameters.TimestampKey, request)) {
      InstallParameters.apply
    }

  def parseShop(request: HmacRequest[_]): String Or Every[String] =
    request.getQueryString(InstallParameters.ShopKey) match {
      case Some(shop) => Good(shop)
      case None => Bad(One(MissingShopMessage))
    }

  def unbind(parameters: InstallParameters): String =
    Vector(
      s"${InstallParameters.ShopKey}=${parameters.shop}",
      InstantBinding.unbind(InstallParameters.TimestampKey, parameters.timestamp)
    )
    .sorted
    .mkString("&")
}
