package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.play.hmac.HmacStubs._
import com.github.fulrich.testcharged.generators._
import play.api.mvc.ControllerComponents
import play.api.test.Helpers.stubControllerComponents


object InstallationStubs {
  def stubInstallAction()(implicit cc: ControllerComponents = stubControllerComponents()): InstallAction =
    new InstallAction()(cc.executionContext)

  def stubAuthorizeAction()(implicit cc: ControllerComponents = stubControllerComponents()): AuthorizeAction =
    new AuthorizeAction()(cc.executionContext)

  def stubInstallActions(configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value)
                        (implicit cc: ControllerComponents = stubControllerComponents()): InstallActions = {
    new InstallActions(stubHmacAction(configuration), stubInstallAction(), stubAuthorizeAction())
  }
}
