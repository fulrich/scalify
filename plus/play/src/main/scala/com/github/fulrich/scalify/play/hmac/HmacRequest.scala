package com.github.fulrich.scalify.play.hmac

import io.lemonlabs.uri.QueryString
import play.api.mvc.{Request, WrappedRequest}


class HmacRequest[A](val payload: QueryString, request: Request[A]) extends WrappedRequest[A](request)
