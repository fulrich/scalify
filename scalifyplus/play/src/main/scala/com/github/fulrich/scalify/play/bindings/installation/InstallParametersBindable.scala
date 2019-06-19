package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.bindings.{BindableFromBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.InstallParametersUrlBinding


object InstallParametersBindable
  extends BindableFromBinding(InstallParametersUrlBinding)
  with EmptyKeyQueryStringBindable[InstallParameters]