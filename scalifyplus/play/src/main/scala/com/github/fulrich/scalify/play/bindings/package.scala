package com.github.fulrich.scalify.play

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import play.api.mvc.QueryStringBindable


package object bindings {
  implicit val timestampQueryStringBindable: QueryStringBindable[Instant] = InstantBindable

  implicit val installParametersQueryStringBindable: QueryStringBindable[InstallParameters] = InstallParametersBindable
}
