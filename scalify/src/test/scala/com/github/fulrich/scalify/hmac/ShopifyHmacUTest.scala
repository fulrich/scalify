package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.QueryString
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers, OptionValues}


class ShopifyHmacUTest extends FunSuite with Matchers with GeneratorDrivenPropertyChecks with OptionValues {
  test("ShopifyHmac can validate an HMAC value using the same key") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val shopifyHmac = new ShopifyHmac()
      val payload = Generate.alphaNumeric.value
      val expectedHmac = shopifyHmac.calculateHmac(payload)

      shopifyHmac.validate(expectedHmac, payload) shouldBe true
    }
  }

  test("ShopifyHmac has a convenience methods on the object can validate and calculate HMAC") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val payload = Generate.alphaNumeric.value
      val expectedHmac = ShopifyHmac.calculateHmac(payload)

      ShopifyHmac.validate(expectedHmac, payload) shouldBe true
    }
  }

  test("ShopifyHmac can consistently calculate an Hmac for any object") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val stringPayload = Generate.alphaNumeric.value
      val intPayload = Generate.int.value

      ShopifyHmac.calculateHmac(stringPayload) shouldBe ShopifyHmac.calculateHmac(stringPayload)
      ShopifyHmac.calculateHmac(intPayload) shouldBe ShopifyHmac.calculateHmac(intPayload.toString)
    }
  }

  test("ShopifyHmac will always calculate Hmac for a QueryString in lexicographical order") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val keyValue1 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue2 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue3 = Generate.alpha.small.value -> Generate.alpha.small.value

      val query1 = QueryString.fromPairs(keyValue1, keyValue2, keyValue3)
      val query2 = QueryString.fromPairs(keyValue3, keyValue2, keyValue1)

      ShopifyHmac.calculateHmac(query1) shouldBe ShopifyHmac.calculateHmac(query2)
    }
  }

  test("Can validate a QueryString to ensure a valid Hmac") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val keyValue1 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue2 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue3 = Generate.alpha.small.value -> Generate.alpha.small.value

      val query = QueryString.fromPairs(keyValue1, keyValue2, keyValue3)

      val validQuery = query.addParam(ShopifyHmac.HmacKey, ShopifyHmac.calculateHmac(query))
      val invalidQuery = query.addParam(ShopifyHmac.HmacKey, Generate.alphaNumeric.value)

      ShopifyHmac.validate(query) shouldBe None
      ShopifyHmac.validate(validQuery).value shouldBe Valid(query)
      ShopifyHmac.validate(invalidQuery).value shouldBe Invalid
    }
  }

  test("Can apply an Hmac to a QueryString") {
    forAll(ShopifyConfigurationGenerator()) { implicit config =>
      val keyValue1 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue2 = Generate.alpha.small.value -> Generate.alpha.small.value
      val keyValue3 = Generate.alpha.small.value -> Generate.alpha.small.value

      val query = QueryString.fromPairs(keyValue1, keyValue2, keyValue3)
      val hmacQuery = ShopifyHmac.addHmac(query)

      hmacQuery.param(ShopifyHmac.HmacKey) shouldBe Some(ShopifyHmac.calculateHmac(query))
      ShopifyHmac.validate(hmacQuery).value shouldBe Valid(query)
    }
  }
}
