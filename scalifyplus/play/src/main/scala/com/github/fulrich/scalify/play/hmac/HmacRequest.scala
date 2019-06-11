package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.{Hmac, HmacQueryString}
import play.api.mvc.Request


object HmacRequest {
  def forQueryString[A](request: Request[A], configuration: ShopifyConfiguration): Hmac[Request[A]] = {
    val hmacQueryString = HmacQueryString(request.rawQueryString)
    val hmac = Hmac(hmacQueryString.hmac, hmacQueryString.queryString)(configuration)

    hmac.map(_ => request)
  }
}
