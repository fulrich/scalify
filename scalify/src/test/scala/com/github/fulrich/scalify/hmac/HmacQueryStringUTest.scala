package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}


class HmacQueryStringUTest extends FunSuite with Matchers {
  implicit val testConfiguration: ShopifyConfiguration = ShopifyConfigurationGenerator().value


  test("Can parse the hmac and rest of the query string from pure query strings") {
    val hmac = Generate.alpha.value
    val queryString = s"${Generate.alpha.value}=${Generate.alpha.value}"
    val rawQueryString = s"?hmac=$hmac&$queryString"
    val expected = new HmacQueryString(rawQueryString, Some(hmac), Some(queryString))

    HmacQueryString(rawQueryString) shouldBe expected
  }

  test("Will return None for the hmac if the query string does not contain it") {
    val queryString = s"${Generate.alpha.value}=${Generate.alpha.value}"
    val rawQueryString = s"?$queryString"
    val expected = new HmacQueryString(rawQueryString, None, Some(queryString))

    HmacQueryString(rawQueryString) shouldBe expected
  }

  test("Will return None for the query string if none is available") {
    val rawQueryString = s""
    val expected = new HmacQueryString(rawQueryString, None, None)

    HmacQueryString(rawQueryString) shouldBe expected
  }

  test("Can convert to an Hmac monad") {
    val queryString = s"${Generate.alpha.value}=${Generate.alpha.value}"
    val validHmac = ShopifyHmac.calculateHmac(queryString)
    val rawQueryString = s"?hmac=$validHmac&$queryString"

    HmacQueryString(rawQueryString).toHmac shouldBe Valid(queryString)
  }
}
