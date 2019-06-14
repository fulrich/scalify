package com.github.fulrich.scalify.play.bindings

import java.time.Instant

import play.api.mvc.QueryStringBindable.Parsing


object InstantBindable extends Parsing[Instant](
  timestamp => Instant.ofEpochSecond(timestamp.toLong),
  instant => instant.getEpochSecond.toString,
  (key: String, e: Exception) => "Cannot parse parameter %s as Instant: %s".format(key, e.getMessage)
)
