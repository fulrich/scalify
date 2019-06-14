package com.github.fulrich.scalify.generators.installation

import com.github.fulrich.scalify.installation.InstallConfirmation
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object InstallConfirmationGenerator {
  def apply(): Gen[InstallConfirmation] =
    for {
      shop <- Generate.alpha
      code <- Generate.alpha
      nonce <- Generate.alpha
      timestamp <- Generate.instant.recent
    } yield InstallConfirmation(
      shop = shop,
      authorizationCode = code,
      nonce = nonce,
      timestamp = timestamp
    )
}
