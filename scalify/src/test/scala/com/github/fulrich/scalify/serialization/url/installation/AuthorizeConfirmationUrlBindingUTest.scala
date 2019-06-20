package com.github.fulrich.scalify.serialization.url.installation

import com.github.fulrich.scalify.generators.installation.AuthorizeConfirmationGenerator
import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.Url
import org.scalatest.{FunSuite, Matchers}


class AuthorizeConfirmationUrlBindingUTest extends FunSuite with Matchers {
  val testUrl = Url(scheme = "http", host = "store.shopify.com")

  test("Can unbind and bind an AuthorizeConfirmation value") {
    val installParameters = AuthorizeConfirmationGenerator().value

    val unboundAuthorizeConfirmation = AuthorizeConfirmationUrlBinding.unbind(installParameters)
    val uriWithUnboundAuthorizeConfirmation = testUrl.withQueryString(unboundAuthorizeConfirmation).toString()

    AuthorizeConfirmationUrlBinding.bind(uriWithUnboundAuthorizeConfirmation).get shouldBe installParameters
  }
}
