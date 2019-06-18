package com.github.fulrich.scalify.generators.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object InstallConfirmationGenerator {
  def apply(): Gen[AuthorizeConfirmation] =
    for {
      shop <- Generate.alpha
      code <- Generate.alpha
      nonce <- Generate.alpha
      timestamp <- Generate.instant.recent
    } yield AuthorizeConfirmation(
      shop = shop,
      authorizationCode = code,
      nonce = nonce,
      timestamp = timestamp
    )
}
