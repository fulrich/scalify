package com.github.fulrich.scalify.serialization.url.base

import com.github.fulrich.testcharged.generators._
import org.scalactic.Good
import org.scalatest.{FunSuite, Matchers}


class StringUrlBindingUTest extends FunSuite with Matchers {
  val testKey = "someString"
  val testDomain: String = "http://store.shopify.com"

  test("Can unbind and bind a String value") {
    val testString = Generate.alpha.value

    val unboundString = StringUrlBinding.unbind(testKey, testString)
    StringUrlBinding.bind(testKey, s"$testDomain?$unboundString") shouldBe Good(testString)
  }
}
