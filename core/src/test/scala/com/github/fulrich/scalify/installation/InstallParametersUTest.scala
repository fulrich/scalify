package com.github.fulrich.scalify.installation

import java.time.Instant

import org.scalatest.{FunSuite, Matchers, OptionValues}
import com.github.fulrich.testcharged.generators._


class InstallParametersUTest extends FunSuite with Matchers with OptionValues {
  val shop: String = Generate.alpha.value
  val timestamp: Instant = Generate.instant.recent.value

  test("Both shop and timestamp are required for creating InstallParameters") {
    InstallParameters(None, Some(timestamp)) shouldBe None
    InstallParameters(Some(shop), None) shouldBe None
    InstallParameters(Some(shop), Some(timestamp)).value shouldBe InstallParameters(shop, timestamp)
  }
}
