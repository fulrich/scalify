package com.github.fulrich.scalify.serialization.url.installation

import com.github.fulrich.scalify.generators.installation.InstallParametersGenerator
import com.github.fulrich.testcharged.generators._
import io.lemonlabs.uri.Url
import org.scalatest.{FunSuite, Matchers}


class InstallParametersUrlBindingUTest extends FunSuite with Matchers {
  val testUrl = Url(scheme = "http", host = "store.shopify.com")

  test("Can unbind and bind an InstallParameters value") {
    val installParameters = InstallParametersGenerator().value

    val unboundInstallParameters = InstallParametersUrlBinding.unbind(installParameters)
    val uriWithUnboundInstallParameters = testUrl.withQueryString(unboundInstallParameters).toString

    InstallParametersUrlBinding.bind(uriWithUnboundInstallParameters).get shouldBe installParameters
  }
}
