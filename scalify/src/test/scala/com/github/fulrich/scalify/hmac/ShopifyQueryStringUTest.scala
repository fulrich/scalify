package com.github.fulrich.scalify.hmac

import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.QueryString
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}


class ShopifyQueryStringUTest extends FunSuite with Matchers with GeneratorDrivenPropertyChecks {
  test("Will sort a QueryString lexicographically based on the keys") {
    val value1 = Generate.alpha.tiny.value
    val value2 = Generate.alpha.tiny.value
    val value3 = Generate.alpha.tiny.value

    val query = QueryString.fromPairs("c" -> value1, "b" -> value2, "a" -> value3)
    val sortedString = ShopifyQueryString.sorted(query).toString

    sortedString shouldBe s"?a=$value3&b=$value2&c=$value1"
  }

  val keyValueGenerator: Gen[(String, String)] = for {
    key <- Generate.alpha.small
    value <- Generate.alpha.small
  } yield key -> value

  test("Can create lexicographically sorted QueryStrings ") {
    forAll(keyValueGenerator, keyValueGenerator, keyValueGenerator) { (keyValue1, keyValue2, keyValue3) =>
      val query1 = QueryString.fromPairs(keyValue1, keyValue2, keyValue3)
      val query2 = QueryString.fromPairs(keyValue3, keyValue2, keyValue1)

      ShopifyQueryString.toSortedString(query1) shouldBe ShopifyQueryString.toSortedString(query2)
    }
  }
}
