package com.github.fulrich.scalify.generators.installation

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.installation.{AuthorizeRedirect, InstallParameters}
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object AuthorizeRedirectGenerator {
  def apply(): Gen[AuthorizeRedirect] =
    withConfig(ShopifyConfigurationGenerator().value)

  def withConfig(implicit configuration: ShopifyConfiguration): Gen[AuthorizeRedirect] =
    fromParameters(InstallParametersGenerator().value)

  def fromParameters(parameters: InstallParameters)(implicit configuration: ShopifyConfiguration): Gen[AuthorizeRedirect] =
    for {
      callbackUri <- Generate.alpha
      nonce <- Generate.uuid.map(_.toString)
    } yield AuthorizeRedirect(configuration.apiKey, parameters.shop, callbackUri, configuration.scopes, nonce)
}
