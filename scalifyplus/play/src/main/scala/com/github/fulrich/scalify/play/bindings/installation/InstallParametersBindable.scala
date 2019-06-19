package com.github.fulrich.scalify.play.bindings.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.bindings.{BindableFromObjectBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.InstallParametersUrlBinding


object InstallParametersBindable
  extends BindableFromObjectBinding(InstallParametersUrlBinding)
  with EmptyKeyQueryStringBindable[InstallParameters]