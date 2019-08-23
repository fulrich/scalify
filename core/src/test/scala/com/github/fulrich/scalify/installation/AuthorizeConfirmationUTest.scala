package com.github.fulrich.scalify.installation

import com.github.fulrich.scalify.generators.installation.AuthorizeConfirmationGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}


class AuthorizeConfirmationUTest extends FunSuite with Matchers {
  test("Can validate the nonce value on the InstallConfirmation") {
    val installConfirmation = AuthorizeConfirmationGenerator().value

    installConfirmation.validateNonce(installConfirmation.nonce) shouldBe true
    installConfirmation.validateNonce(Generate.alpha.value) shouldBe false

    installConfirmation.validateNonce(Some(installConfirmation.nonce)) shouldBe true
    installConfirmation.validateNonce(Some(Generate.alpha.value)) shouldBe false
    installConfirmation.validateNonce(None) shouldBe false
  }
}
