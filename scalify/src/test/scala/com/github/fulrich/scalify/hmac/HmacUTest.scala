package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.QueryString
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}


class HmacUTest extends FunSuite with Matchers with GeneratorDrivenPropertyChecks {
  implicit val configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value

  test("A valid hmac will be constructed as Valid") {
    forAll(Generate.alphaNumeric.large) { payload =>
      val validHmac = ShopifyHmac.calculateHmac(payload)

      Hmac(validHmac, payload).isValid shouldBe true
      Hmac(validHmac, payload) shouldBe a[Valid[_]]
    }
  }

  test("An invalid hmac will return  as Invalid") {
    forAll(Generate.alphaNumeric.large, Generate.alphaNumeric.large) { (invalidHmac, payload) =>
      Hmac(invalidHmac, payload).isValid shouldBe false
      Hmac(invalidHmac, payload) shouldBe Invalid
    }
  }

  test("The Optional constructor will only be valid if both parts are present and the HMAC is valid") {
    val payload = Generate.alpha.value
    val validHmac = ShopifyHmac.calculateHmac(payload)

    Hmac(None, Some(payload)) shouldBe Invalid
    Hmac(Some(validHmac), None) shouldBe Invalid
    Hmac(Some(validHmac), Some(payload)) shouldBe Valid(payload)
  }

  test("Can map on an Hmac and it will modify the Valid value") {
    val payload = Generate.alpha.value
    val validHmac = ShopifyHmac.calculateHmac(payload)
    val hmac = Hmac(validHmac, payload)

    hmac.map(_.capitalize).get shouldBe payload.capitalize
  }

  test ("Can use getOrElse to get the Valid value or a default") {
    val payload = Generate.alpha.value
    val correctHmac = ShopifyHmac.calculateHmac(payload)

    val validHmac = Hmac(correctHmac, payload)
    val invalidHmac = Hmac(Generate.alpha.value, payload)

    val default = Generate.alpha.value
    validHmac.getOrElse(default) shouldBe payload
    invalidHmac.getOrElse(default) shouldBe default
  }

  test("Can translate any Hmac in an option") {
    val payload = Generate.alpha.value
    Valid(payload).toOption shouldBe Some(payload)
    Invalid.toOption shouldBe None
  }

  test("Can build an Hmac from a QueryString") {
    val query = QueryString.fromPairs(
      Generate.alpha.tiny.value -> Generate.alpha.small.value,
      Generate.alpha.tiny.value -> Generate.alpha.small.value,
      Generate.alpha.tiny.value -> Generate.alpha.small.value
    )

    val validHmac = ShopifyHmac.calculateHmac(query)
    val invalidHmac = Generate.alphaNumeric.value

    Hmac(validHmac, query) shouldBe Valid(query)
    Hmac(invalidHmac, query) shouldBe Invalid
  }
}
