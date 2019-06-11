package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.play.TestApplication
import org.scalatest.{FunSuite, Matchers}


class HmacActionUTest extends FunSuite with Matchers with TestApplication {
  test("Can use the injected HmacAction") {
    instanceOf[HmacAction] shouldBe a[HmacAction]
  }
}
