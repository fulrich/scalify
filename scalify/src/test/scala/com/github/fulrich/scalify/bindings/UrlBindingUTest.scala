package com.github.fulrich.scalify.bindings

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.scalify.serialization.url.UrlBinding
import io.lemonlabs.uri.QueryString
import org.scalactic.{Bad, Every, Good, One, Or}
import org.scalatest.{FunSuite, Matchers, OptionValues}

import scala.util.{Failure, Success, Try}


class UrlBindingUTest extends FunSuite with Matchers with OptionValues {
  object TestIntBinding extends UrlBinding[Int] {
    override def bind(key: String, query: QueryString): Option[Or[Int, Every[ScalifyError]]] =
      query.param(key).map { paramValue =>
        Try(paramValue.toInt) match {
          case Success(intValue) => Good(intValue)
          case Failure(_) => Bad(One(ScalifyError("Failed to parse")))
        }
      }
  }

  test("Can use the standard QueryString bind to parse a key") {
    TestIntBinding.bind("int", QueryString(Vector("int" -> Some("25")))).value.get shouldBe 25
    TestIntBinding.bind("int", QueryString(Vector.empty)) shouldBe None
  }

  test("Can use the required QueryString bind to parse a key") {
    TestIntBinding.requiredBind("int", QueryString(Vector("int" -> Some("25")))).get shouldBe 25
    TestIntBinding.requiredBind("int", QueryString(Vector.empty)) shouldBe TestIntBinding.missingError(Some("int"))
  }

  test("Can use the standard full URI bind to parse a key") {
    TestIntBinding.bind("int", "http://www.website.com?int=25").value.get shouldBe 25
    TestIntBinding.bind("int", "http://www.website.com") shouldBe None
  }

  test("Can use the required full URI bind to parse a key") {
    TestIntBinding.requiredBind("int", "http://www.website.com?int=25").get shouldBe 25
    TestIntBinding.requiredBind("int", "http://www.website.com") shouldBe TestIntBinding.missingError(Some("int"))
  }

  test("Can use the standard QueryMap bind to parse a key") {
    TestIntBinding.bind("int", Map("int" -> Seq("25"))).value.get shouldBe 25
    TestIntBinding.bind("int", Map.empty[String, Seq[String]]) shouldBe None
  }

  test("Can use the required QueryMap bind to parse a key") {
    TestIntBinding.requiredBind("int", Map("int" -> Seq("25"))).get shouldBe 25
    TestIntBinding.requiredBind("int", Map.empty[String, Seq[String]]) shouldBe TestIntBinding.missingError(Some("int"))
  }

  test("The simple default unbind will take the bound value and unbind it") {
    TestIntBinding.unbind("int", 25) shouldBe "int=25"
  }
}
