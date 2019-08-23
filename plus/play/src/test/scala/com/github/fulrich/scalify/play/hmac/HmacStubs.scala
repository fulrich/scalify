package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import play.api.mvc.{BodyParsers, ControllerComponents}
import play.api.test.Helpers._
import com.github.fulrich.testcharged.generators._


object HmacStubs {
  def stubHmacAction(configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value)
                    (implicit cc: ControllerComponents = stubControllerComponents()): HmacAction = {
    val parser = new BodyParsers.Default(cc.parsers)
    new HmacAction(parser, configuration)(cc.executionContext)
  }
}
