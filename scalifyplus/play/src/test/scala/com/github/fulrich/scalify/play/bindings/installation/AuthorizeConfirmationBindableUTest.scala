package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.generators.installation.InstallConfirmationGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.{EitherValues, FunSuite, Matchers, OptionValues}
import play.api.test.FakeRequest
import play.api.test.Helpers.GET


class AuthorizeConfirmationBindableUTest extends FunSuite with Matchers with OptionValues with EitherValues {

  test("Can bind and unbind InstallConfirmation from a Query String") {
    val installConfirmation = InstallConfirmationGenerator().value

    val unboundInstallConfirmation = AuthorizeConfirmationBindable.unbind(installConfirmation)
    println(unboundInstallConfirmation)
    val fakeRequest = FakeRequest(GET, s"/installCallback?$unboundInstallConfirmation")
    println(AuthorizeConfirmationBindable.bind(fakeRequest.queryString))

    AuthorizeConfirmationBindable.bind(fakeRequest.queryString).value.right.value shouldBe installConfirmation
  }
}
