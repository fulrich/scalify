package com.github.fulrich.scalify.play.play

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.hmac.{Invalid, ShopifyHmac, Valid}
import com.github.fulrich.scalify.play.hmac.HmacRequest
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}
import play.api.test.FakeRequest
import play.api.test.Helpers._


class HmacRequestUTest extends FunSuite with Matchers {
  val configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value

  test("If the request contains a valid Hmac it will be returned as a Valid[Request]") {
    val parameters = "shop=fredsdevstore.myshopify.com&timestamp=1557768838"
    val validHmac = ShopifyHmac.calculateHmac(parameters)(configuration)
    val request = FakeRequest(GET, s"/install?hmac=$validHmac&$parameters")

    HmacRequest.forQueryString(request, configuration) shouldBe Valid(request)
  }

  test("If the request contains an invalid Hmac it will be returned as a Invalid") {
    val parameters = "shop=fredsdevstore.myshopify.com&timestamp=1557768838"
    val invalidHmac = Generate.alpha.value
    val request = FakeRequest(GET, s"/install?hmac=$invalidHmac&$parameters")

    HmacRequest.forQueryString(request, configuration) shouldBe Invalid
  }
}
