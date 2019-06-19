package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.scalify.ScalifyError
import io.lemonlabs.uri.QueryString
import org.scalactic._
import org.scalatest.{FunSuite, Matchers, OptionValues}

import scala.util.{Failure, Success, Try}


class UrlBindingUTest extends FunSuite with Matchers with OptionValues {
  val Key: String = "int"
  
  object TestIntBinding extends UrlBinding[Int] {
    override def optionalBind(key: String, query: QueryString): Option[Or[Int, Every[ScalifyError]]] =
      query.param(key).map { paramValue =>
        Try(paramValue.toInt) match {
          case Success(intValue) => Good(intValue)
          case Failure(_) => Bad(One(ScalifyError("Failed to parse")))
        }
      }
  }

  test("Can use the standard QueryString bind to parse a key") {
    TestIntBinding.optionalBind(Key, QueryString(Vector(Key -> Some("25")))).value.get shouldBe 25
    TestIntBinding.optionalBind(Key, QueryString(Vector.empty)) shouldBe None
  }

  test("Can use the required QueryString bind to parse a key") {
    TestIntBinding.bind(Key, QueryString(Vector(Key -> Some("25")))).get shouldBe 25
    TestIntBinding.bind(Key, QueryString(Vector.empty)) shouldBe TestIntBinding.missingError(Key)
  }

  test("Can use the standard full URI bind to parse a key") {
    TestIntBinding.optionalBind(Key, "http://www.website.com?int=25").value.get shouldBe 25
    TestIntBinding.optionalBind(Key, "http://www.website.com") shouldBe None
  }

  test("Can use the required full URI bind to parse a key") {
    TestIntBinding.bind(Key, "http://www.website.com?int=25").get shouldBe 25
    TestIntBinding.bind(Key, "http://www.website.com") shouldBe TestIntBinding.missingError(Key)
  }

  test("Can use the standard QueryMap bind to parse a key") {
    TestIntBinding.optionalBind(Key, Map(Key -> Seq("25"))).value.get shouldBe 25
    TestIntBinding.optionalBind(Key, Map.empty[String, Seq[String]]) shouldBe None
  }

  test("Can use the required QueryMap bind to parse a key") {
    TestIntBinding.bind(Key, Map(Key -> Seq("25"))).get shouldBe 25
    TestIntBinding.bind(Key, Map.empty[String, Seq[String]]) shouldBe TestIntBinding.missingError(Key)
  }

  test("The simple default unbind will take the bound value and unbind it") {
    TestIntBinding.unbind(Key, 25) shouldBe "int=25"
  }
}
