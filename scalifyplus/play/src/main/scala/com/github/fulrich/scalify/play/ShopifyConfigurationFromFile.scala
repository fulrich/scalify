package com.github.fulrich.scalify.play

import com.github.fulrich.scalify.ShopifyConfiguration
import pureconfig.{CamelCase, ConfigFieldMapping}
import pureconfig.generic.ProductHint
import pureconfig.generic.auto._


object ShopifyConfigurationFromFile {
  val ShopifyNamespace = "shopify"

  implicit def hint[T]: ProductHint[T] = ProductHint[T](ConfigFieldMapping(CamelCase, CamelCase))

  def load(): ShopifyConfiguration = pureconfig.loadConfigOrThrow[ShopifyConfiguration](ShopifyNamespace)

  val Loaded: ShopifyConfiguration = load()
}
