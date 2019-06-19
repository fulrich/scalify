package com.github.fulrich.scalify.serialization.url.installation

import com.github.fulrich.scalify.installation.{AuthorizeConfirmation, InstallParameters}
import com.github.fulrich.scalify.serialization.url.UrlBinding


trait InstallationUrlBindings {
  implicit val authorizeConfirmationUrlBinding: UrlBinding[AuthorizeConfirmation] = AuthorizeConfirmationUrlBinding
  implicit val installParametersUrlBinding: UrlBinding[InstallParameters] = InstallParametersUrlBinding
}
