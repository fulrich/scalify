package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.bindings.{BindableFromBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.AuthorizeConfirmationUrlBinding


object AuthorizeConfirmationBindable
  extends BindableFromBinding(AuthorizeConfirmationUrlBinding)
  with EmptyKeyQueryStringBindable[AuthorizeConfirmation]