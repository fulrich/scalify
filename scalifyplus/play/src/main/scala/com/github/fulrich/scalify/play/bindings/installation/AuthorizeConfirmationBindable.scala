package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.bindings.{BindableFromObjectBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.AuthorizeConfirmationUrlBinding


object AuthorizeConfirmationBindable
  extends BindableFromObjectBinding(AuthorizeConfirmationUrlBinding)
  with EmptyKeyQueryStringBindable[AuthorizeConfirmation]