package com.github.fulrich.scalify.play.serialization.url.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.serialization.url.{BindableFromObjectBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.AuthorizeConfirmationUrlBinding

object AuthorizeConfirmationBindable
  extends BindableFromObjectBinding(AuthorizeConfirmationUrlBinding)
  with EmptyKeyQueryStringBindable[AuthorizeConfirmation]
