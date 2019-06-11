package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import io.lemonlabs.uri.QueryString


case class HmacQueryString(rawQueryString: String, hmac: Option[String] = None, queryString: Option[String] = None) {
  def toHmac(implicit configuration: ShopifyConfiguration): Hmac[String] = Hmac(hmac, queryString)
}

object HmacQueryString {
  val HmacKey = "hmac"
  val QueryStringDelimiter = "?"
  val Nothing = ""

  def apply(queryString: String): HmacQueryString = {
    val parsedQuery = Option(queryString).filter(_.nonEmpty).flatMap(QueryString.parseOption)
    val queryWithoutHmac = parsedQuery.map(_.removeAll(HmacKey).toString.replace(QueryStringDelimiter, Nothing))
    val parsedHmac = parsedQuery.flatMap(_.param(HmacKey))

    new HmacQueryString(queryString, parsedHmac, queryWithoutHmac)
  }
}
