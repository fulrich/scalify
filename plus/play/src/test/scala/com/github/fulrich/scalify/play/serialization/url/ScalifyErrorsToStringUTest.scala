package com.github.fulrich.scalify.play.serialization.url

import com.github.fulrich.scalify.generators.ScalifyErrorGenerator
import org.scalatest.{FunSuite, Matchers}
import com.github.fulrich.testcharged.generators._


class ScalifyErrorsToStringUTest extends FunSuite with Matchers {

  test("Can convert a single ScalifyError to a string") {
    val error = ScalifyErrorGenerator().value

    val key = Generate.alpha.tiny.value
    val field = Generate.alpha.tiny.value

    ScalifyErrorsToString(key, error.copy(field = None)) shouldBe s"$key: ${error.message}"
    ScalifyErrorsToString(key, error.copy(field = Some(field))) shouldBe s"$field: ${error.message}"
  }

  test("Can convert multiple ScalifyErrors to a single string") {
    val errors = ScalifyErrorGenerator().seq
    val key = Generate.alpha.tiny.value

    val result = ScalifyErrorsToString(key, errors)

    errors.foreach { error =>
      result should include (error.message)
    }
  }
}
