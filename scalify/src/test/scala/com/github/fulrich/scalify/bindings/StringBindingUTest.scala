package com.github.fulrich.scalify.bindings

import com.github.fulrich.scalify.serialization.url.base.StringBinding
import com.github.fulrich.testcharged.generators._
import org.scalactic.Good
import org.scalatest.{FunSuite, Matchers}


class StringBindingUTest extends FunSuite with Matchers {
  val testKey = "someString"
  val testDomain: String = "http://store.shopify.com"

  test("Can unbind and bind a String value") {
    val testString = Generate.alpha.value

    val unboundString = StringBinding.unbind(testKey, testString)
    StringBinding.requiredBind(testKey, s"$testDomain?$unboundString") shouldBe Good(testString)
  }
}
