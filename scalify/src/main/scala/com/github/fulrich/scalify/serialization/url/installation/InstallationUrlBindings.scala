package com.github.fulrich.scalify.serialization.url.installation

import com.github.fulrich.scalify.installation.{AuthorizeConfirmation, InstallParameters}
import com.github.fulrich.scalify.serialization.url.ObjectUrlBinding


trait InstallationUrlBindings {
  implicit val authorizeConfirmationUrlBinding: ObjectUrlBinding[AuthorizeConfirmation] = AuthorizeConfirmationUrlBinding
  implicit val installParametersUrlBinding: ObjectUrlBinding[InstallParameters] = InstallParametersUrlBinding
}
