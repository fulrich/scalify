package com.github.fulrich.scalify.serialization.url.installation

import com.github.fulrich.scalify.generators.installation.InstallParametersGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}


class InstallParametersUrlBindingUTest extends FunSuite with Matchers {
  test("Can unbind and bind an Instant value") {
    val installParameters = InstallParametersGenerator().value

    println(InstallParametersUrlBinding.unbind(installParameters))
  }
}
