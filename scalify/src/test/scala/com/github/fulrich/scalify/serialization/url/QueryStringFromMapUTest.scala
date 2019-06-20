package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}


class QueryStringFromMapUTest extends FunSuite with Matchers {
  test("Converts a Map to a QueryString") {
    val testKey1 = Generate.alpha.value
    val testValue1 = Generate.alphaNumeric.value

    val testKey2 = Generate.alpha.value
    val testValue2 = Generate.alphaNumeric.value

    val testMap = Map(
      testKey1 -> Seq(testValue1),
      testKey2 -> Seq(testValue2)
    )

    val result = QueryStringFromMap(testMap)
    result.param(testKey1) shouldBe Some(testValue1)
    result.param(testKey2) shouldBe Some(testValue2)
  }
}
