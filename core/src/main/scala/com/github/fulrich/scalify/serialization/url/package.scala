package com.github.fulrich.scalify.serialization

import com.github.fulrich.scalify.serialization.url.base.BaseUrlBindings
import com.github.fulrich.scalify.serialization.url.installation.InstallationUrlBindings


package object url
  extends UrlBindingTypes
  with UrlBindingDsl
  with BaseUrlBindings
  with InstallationUrlBindings
