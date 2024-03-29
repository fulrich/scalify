package com.github.fulrich.scalify.play.serialization.url.base

import java.time.Instant

import com.github.fulrich.scalify.play.serialization.url.BindableFromBinding
import com.github.fulrich.scalify.serialization.url.base.InstantUrlBinding

object InstantBindable
  extends BindableFromBinding[Instant](InstantUrlBinding)
