package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.play.hmac.HmacAction
import javax.inject.Inject

class InstallActions @Inject()(hmacAction: HmacAction, installAction: InstallAction) {
  def installParameters = hmacAction.andThen(installAction)
}
