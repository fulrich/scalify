package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.{QueryString, Url}
import org.scalactic.Accumulation.withGood
import org.scalatest.{FunSuite, Matchers}


class ObjectUrlBindingUTest extends FunSuite with Matchers {
  val testUrl = Url(scheme = "http", host = "store.shopify.com" )

  val Key1: String = Generate.alpha.value
  val Key2: String = Generate.alpha.value

  case class TestObject(name: String, other: String)
  object TestObjectBinding extends ObjectUrlBinding[TestObject] {
    override def bind(query: QueryString): UrlBind[TestObject] =
      withGood(bind[String](Key1, query), bind[String](Key2, query))(TestObject.apply)

    override def unbind(value: TestObject): QueryString = unbindList(
      unbind(Key1, value.name),
      unbind(Key2, value.other)
    )
  }

  test("Can parse an object from an entire URI string") {
    val testObject = TestObject(Generate.alpha.value, Generate.alpha.value)
    val url = testUrl.withQueryString(TestObjectBinding.unbind(testObject)).toString

    TestObjectBinding.bind(url).get shouldBe testObject
  }

  test("Can parse an object from a query map") {
    val testObject = TestObject(Generate.alpha.value, Generate.alpha.value)
    val queryMap = Map(
      Key1 -> Seq(testObject.name),
      Key2 -> Seq(testObject.other)
    )

    TestObjectBinding.bind(queryMap).get shouldBe testObject
  }

  test("Can parse an object from a QueryString") {
    val testObject = TestObject(Generate.alpha.value, Generate.alpha.value)
    val queryString = QueryString(Vector.empty).addParam(Key1, testObject.name).addParam(Key2, testObject.other)

    TestObjectBinding.bind(queryString).get shouldBe testObject
  }

  test("Can combine a list of unbound values to a URL string") {
    val value1: QueryString = QueryString.fromPairs(Key1 -> Generate.alpha.value)
    val value2: QueryString = QueryString.fromPairs(Key2 -> Generate.alpha.value)

    TestObjectBinding.unbindList(value1, value2) shouldBe value1.addParams(value2)
  }
}
