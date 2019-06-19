package com.github.fulrich.scalify.serialization

import com.github.fulrich.scalify.ScalifyError
import com.github.fulrich.scalify.serialization.url.installation.InstallationUrlBindings
import org.scalactic.{Every, Or}


package object url
  extends UrlBindingDsl
  with InstallationUrlBindings {

  type UrlBind[A] = A Or Every[ScalifyError]
}
