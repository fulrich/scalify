package com.github.fulrich.scalify.serialization.url

import com.github.fulrich.scalify.ScalifyError
import org.scalactic.{Every, Or}

trait UrlBindingTypes {
  type UrlBind[A] = A Or Every[ScalifyError]
  type QueryMap = Map[String, Seq[String]]
}
