package com.github.fulrich.scalify.play

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.play.hmac.HmacAction
import play.api.Configuration
import play.api.Environment
import play.api.inject.Binding
import play.api.inject.Module


class ScalifyModule extends Module {
  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[ShopifyConfiguration].toInstance(ShopifyConfigurationFromFile.Loaded)
    )
  }
}
