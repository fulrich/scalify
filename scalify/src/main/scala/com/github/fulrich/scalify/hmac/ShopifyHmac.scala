package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
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


object ShopifyHmac {
  val Algorithm = "HmacSHA256"

  def validate(hmac: String, payload: String)(implicit config: ShopifyConfiguration): Boolean =
    new ShopifyHmac().validate(hmac, payload)

  def calculateHmac(payload: String)(implicit config: ShopifyConfiguration): String =
    new ShopifyHmac().calculateHmac(payload)
}
