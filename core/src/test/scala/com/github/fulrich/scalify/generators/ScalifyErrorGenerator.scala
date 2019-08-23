package com.github.fulrich.scalify.generators

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object ScalifyErrorGenerator {
  def apply(): Gen[ScalifyError] = for {
    message <- Generate.alpha
    field <- Generate.alpha.tiny.gen.option
  } yield ScalifyError(message, field)
}
