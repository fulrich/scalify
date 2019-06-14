package com.github.fulrich.scalify.play

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.play.ShopifyConfigurationProvider.ShopifyNamespace
import javax.inject.Provider
import pureconfig.{CamelCase, ConfigFieldMapping}
import pureconfig.generic.ProductHint
import pureconfig.generic.auto._


class ShopifyConfigurationProvider extends Provider[ShopifyConfiguration] {
  implicit def hint[T]: ProductHint[T] = ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))

  override def get(): ShopifyConfiguration =
    pureconfig.loadConfigOrThrow[ShopifyConfiguration](ShopifyNamespace)
}

object ShopifyConfigurationProvider {
  val ShopifyNamespace = "shopify"
}
