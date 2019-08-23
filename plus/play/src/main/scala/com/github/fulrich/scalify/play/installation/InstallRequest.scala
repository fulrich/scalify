package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.hmac.HmacRequest
import play.api.mvc.WrappedRequest


class InstallRequest[A](val parameters: InstallParameters, request: HmacRequest[A]) extends WrappedRequest[A](request)
