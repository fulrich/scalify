package com.github.fulrich.scalify.installation

import org.scalatest.{FunSuite, Matchers, OptionValues}
import com.github.fulrich.testcharged.generators._


class InstallParametersUTest extends FunSuite with Matchers with OptionValues {
  val shop: String = Generate.alpha.value
  val timestamp: String = Generate.alpha.value

  test("Both shop and timestamp are required for creating InstallParameters") {
    InstallParameters(None, Some(timestamp)) shouldBe None
    InstallParameters(Some(shop), None) shouldBe None
    InstallParameters(Some(shop), Some(timestamp)).value shouldBe InstallParameters(shop, timestamp)
  }

  test("Can create InstallParameters from a Map of values") {
    val parameters = Map(
      InstallParameters.ShopKey -> shop,
      InstallParameters.TimestampKey -> timestamp
    )

    InstallParameters.fromMap(parameters).value shouldBe InstallParameters(shop, timestamp)
  }

  test("Can create InstallParameters from a Seq map just ignoring the extra values") {
    val parameters = Map(
      InstallParameters.ShopKey -> Vector(shop, Generate.alpha.value),
      InstallParameters.TimestampKey -> Vector(timestamp, Generate.alpha.value)
    )

    InstallParameters.fromSeqMap(parameters).value shouldBe InstallParameters(shop, timestamp)
  }
}
