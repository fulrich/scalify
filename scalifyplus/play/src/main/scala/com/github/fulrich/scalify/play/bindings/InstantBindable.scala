package com.github.fulrich.scalify.play.bindings

import java.time.Instant

import com.github.fulrich.scalify.serialization.url.base.InstantUrlBinding


object InstantBindable
  extends BindableFromBinding[Instant](InstantUrlBinding)