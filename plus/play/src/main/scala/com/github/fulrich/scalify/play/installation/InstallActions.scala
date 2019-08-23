package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.play.hmac.HmacAction
import javax.inject.Inject


class InstallActions @Inject()(hmacAction: HmacAction, installAction: InstallAction, authorizeAction: AuthorizeAction) {
  def install = hmacAction.andThen(installAction)
  def authorize = hmacAction.andThen(authorizeAction)
}
