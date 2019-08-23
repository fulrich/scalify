package com.github.fulrich.scalify.generators

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.testcharged.generators._
import org.scalacheck.Gen


object ShopifyConfigurationGenerator {
  def apply(): Gen[ShopifyConfiguration] = for {
    apiKey <- Generate.alpha
    apiSecret <- Generate.alphaNumeric
    scopes <- Generate.alpha.tiny.gen.seq
  } yield ShopifyConfiguration(apiKey, apiSecret, scopes)
}
