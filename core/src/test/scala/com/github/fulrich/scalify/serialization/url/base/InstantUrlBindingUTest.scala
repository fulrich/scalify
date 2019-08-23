package com.github.fulrich.scalify.serialization.url.base

import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.{Uri, Url}
import org.scalactic.Good
import org.scalatest.{FunSuite, Matchers}


class InstantUrlBindingUTest extends FunSuite with Matchers {
  val testKey = "timestamp"
  val testUrl = Url(scheme = "http", host = "store.shopify.com")

  test("Can unbind and bind an Instant value") {
    val instant = Generate.instant.recent.value

    val unboundInstant = InstantUrlBinding.unbind(testKey, instant)
    val urlWithInstant = testUrl.withQueryString(unboundInstant).toString

    InstantUrlBinding.bind(testKey, urlWithInstant) shouldBe Good(instant)
  }

  test("An error will be returned in an Instant Value could not be parsed") {
    val invalidInstantParams = testUrl.addParam(testKey, "123invalid123")

    InstantUrlBinding.bind(testKey, invalidInstantParams.toString) shouldBe InstantUrlBinding.invalidTimestamp(testKey)
  }
}
