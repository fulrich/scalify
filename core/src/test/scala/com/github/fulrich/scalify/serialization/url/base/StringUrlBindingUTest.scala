package com.github.fulrich.scalify.serialization.url.base

import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.Url
import org.scalactic.Good
import org.scalatest.{FunSuite, Matchers}


class StringUrlBindingUTest extends FunSuite with Matchers {
  val testKey = "someString"
  val testUrl = Url(scheme = "http", host = "store.shopify.com")

  test("Can unbind and bind a String value") {
    val testString = Generate.alpha.value

    val unboundString = StringUrlBinding.unbind(testKey, testString)
    val uriWithUnboundString = testUrl.withQueryString(unboundString)

    StringUrlBinding.bind(testKey, uriWithUnboundString.toString) shouldBe Good(testString)
  }
}
