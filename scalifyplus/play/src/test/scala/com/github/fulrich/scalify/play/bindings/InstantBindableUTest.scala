package com.github.fulrich.scalify.play.bindings

import com.github.fulrich.testcharged.generators._
import org.scalatest.{EitherValues, FunSuite, Matchers, OptionValues}
import play.api.test.Helpers._
import play.api.test._


class InstantBindableUTest extends FunSuite with Matchers with OptionValues with EitherValues {
  test("Can bind and unbind Instants from a Query String") {
    val instant = Generate.instant.past.value

    val unboundTimestamp = InstantBindable.unbind("instant", instant)
    val requestWithUnboundTimestamp = FakeRequest(GET, s"/install?$unboundTimestamp")

    InstantBindable.bind("instant",requestWithUnboundTimestamp.queryString).value.right.value shouldBe instant
  }
}
