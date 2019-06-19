package com.github.fulrich.scalify.serialization.url.base

import java.time.Instant

import com.github.fulrich.scalify.serialization.url.UrlBinding


trait BaseUrlBindings {
  implicit val stringUrlBinding: UrlBinding[String] = StringBinding
  implicit val instantUrlBinding: UrlBinding[Instant] = InstantBinding
}
