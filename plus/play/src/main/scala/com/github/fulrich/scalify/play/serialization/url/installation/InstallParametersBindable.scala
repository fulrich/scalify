package com.github.fulrich.scalify.play.serialization.url.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.serialization.url.{BindableFromObjectBinding, EmptyKeyQueryStringBindable}
import com.github.fulrich.scalify.serialization.url.installation.InstallParametersUrlBinding

object InstallParametersBindable
  extends BindableFromObjectBinding(InstallParametersUrlBinding)
  with EmptyKeyQueryStringBindable[InstallParameters]
