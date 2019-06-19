package com.github.fulrich.scalify.bindings

import com.github.fulrich.scalify.serialization.url.base.InstantBinding
import com.github.fulrich.testcharged.generators._
import org.scalactic.Good
import org.scalatest.{FunSuite, Matchers}


class InstantBindingUTest extends FunSuite with Matchers {
  val testKey = "timestamp"
  val testDomain: String = "http://store.shopify.com"

  test("Can unbind and bind an Instant value") {
    val instant = Generate.instant.recent.value

    val unboundInstant = InstantBinding.unbind(testKey, instant)
    InstantBinding.requiredBind(testKey, s"$testDomain?$unboundInstant") shouldBe Good(instant)
  }

  test("An error will be returned in an Instant Value could not be parsed") {
    val invalidInstantParams = s"$testDomain?$testKey=123invalid123"

    InstantBinding.requiredBind(testKey, invalidInstantParams) shouldBe InstantBinding.invalidTimestamp(testKey)
  }
}
