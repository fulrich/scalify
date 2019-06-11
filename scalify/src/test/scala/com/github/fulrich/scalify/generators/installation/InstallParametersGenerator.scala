package com.github.fulrich.scalify.generators.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object InstallParametersGenerator {
  def apply(): Gen[InstallParameters] = for {
    shop <- Generate.alpha
    timestamp <- Generate.alpha
  } yield InstallParameters(shop, timestamp)
}
