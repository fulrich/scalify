package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import io.lemonlabs.uri.QueryString
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class ShopifyHmac(implicit val shopifyConfig: ShopifyConfiguration) {
  val secretKey = new SecretKeySpec(shopifyConfig.apiSecret.getBytes, ShopifyHmac.Algorithm)

  def validate(hmac: String, payload: String): Boolean = hmac == calculateHmac(payload)

  def calculateHmac(payload: String): String = {
    val macInstance = Mac.getInstance(ShopifyHmac.Algorithm)
    macInstance.init(secretKey)

    hex(macInstance.doFinal(payload.getBytes))
  }

  private def hex(bytes: Seq[Byte]): String = bytes.map("%02x" format _).mkString
}


object ShopifyHmac extends ShopifyQueryString {
  val HmacKey = "hmac"
  val Algorithm = "HmacSHA256"

  def validate[A](hmac: String, payload: A)(implicit config: ShopifyConfiguration): Boolean =
    new ShopifyHmac().validate(hmac, payload.toString)

  def validate(payload: QueryString)(implicit configuration: ShopifyConfiguration): Option[Hmac[QueryString]] =
    payload.param(HmacKey) map { hmac => Hmac(hmac, payload.removeAll(HmacKey)) }

  def calculateHmac[A](payload: A)(implicit config: ShopifyConfiguration): String =
    new ShopifyHmac().calculateHmac(payload.toString)

  def calculateHmac(query: QueryString)(implicit config: ShopifyConfiguration): String =
    calculateHmac(query.toSortedString)

  def addHmac(payload: QueryString)(implicit configuration: ShopifyConfiguration): QueryString =
    payload.addParam(HmacKey, calculateHmac(payload))
}
