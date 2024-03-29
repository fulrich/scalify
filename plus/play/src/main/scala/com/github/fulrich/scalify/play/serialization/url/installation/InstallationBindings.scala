package com.github.fulrich.scalify.play.serialization.url.installation

import com.github.fulrich.scalify.installation.{AuthorizeConfirmation, InstallParameters}
import play.api.mvc.QueryStringBindable


trait InstallationBindings {
  implicit val installParametersBindable: QueryStringBindable[InstallParameters] = InstallParametersBindable
  implicit val installConfirmationBindable: QueryStringBindable[AuthorizeConfirmation] = AuthorizeConfirmationBindable
}

object InstallationBindings {
  val RouteImports: Seq[String] = Seq(
    classOf[InstallParameters].getCanonicalName,
    classOf[AuthorizeConfirmation].getCanonicalName
  )
}
