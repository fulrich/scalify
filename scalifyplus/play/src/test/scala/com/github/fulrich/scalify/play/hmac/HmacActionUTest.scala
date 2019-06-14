package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.play.ShopifyFakeApplication
import org.scalatest.{FunSuite, Matchers}


class HmacActionUTest extends FunSuite with Matchers with ShopifyFakeApplication {
  test("Can use the injected HmacAction") {
    inject[HmacAction] shouldBe a[HmacAction]
  }
}
