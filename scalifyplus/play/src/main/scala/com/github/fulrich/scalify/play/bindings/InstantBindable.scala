package com.github.fulrich.scalify.play.bindings

import java.time.Instant

import com.github.fulrich.scalify.serialization.url.base.InstantBinding


object InstantBindable
  extends BindableFromBinding[Instant](InstantBinding)