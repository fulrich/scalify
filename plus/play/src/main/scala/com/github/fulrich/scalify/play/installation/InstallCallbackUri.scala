package com.github.fulrich.scalify.play.installation

import play.api.mvc.{Call, Request}


object InstallCallbackUri {
  def apply(request: Request[_], action: Call): String = "https://" + request.host + action.url

  def apply(action: Call)(implicit request: Request[_]): String = apply(request, action)
}
