package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.generators.installation.InstallParametersGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.{EitherValues, FunSuite, Matchers, OptionValues}
import play.api.test.Helpers._
import play.api.test._


class InstallParametersBindableUTest extends FunSuite with Matchers with OptionValues with EitherValues {

  test("Can bind and unbind InstallParameters from a Query String") {
    val installParameters = InstallParametersGenerator().value

    val unboundInstallParameters = InstallParametersBindable.unbind(installParameters)
    val fakeRequest = FakeRequest(GET, s"/install?$unboundInstallParameters")

    InstallParametersBindable.bind(fakeRequest.queryString).value.right.value shouldBe installParameters
  }
}
