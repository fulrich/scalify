package com.github.fulrich.scalify.play

import java.time.Instant

import com.github.fulrich.scalify.installation.{InstallConfirmation, InstallParameters}
import play.api.mvc.QueryStringBindable


package object bindings {
  implicit val timestampBindable: QueryStringBindable[Instant] = InstantBindable

  implicit val installParametersBindable: QueryStringBindable[InstallParameters] = InstallParametersBindable
  implicit val installConfirmationBindable: QueryStringBindable[InstallConfirmation] = InstallConfirmationBindable
}
