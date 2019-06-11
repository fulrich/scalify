package com.github.fulrich.scalify.play

import _root_.play.api.inject.guice.GuiceApplicationBuilder
import _root_.play.api.inject.bind
import _root_.play.api.Application
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._

import scala.reflect.ClassTag


trait TestApplication {
  val application: Application = new GuiceApplicationBuilder()
    .overrides(
      bind[ShopifyConfiguration].toInstance(ShopifyConfigurationGenerator().value)
    )
    .build()

  def instanceOf[A : ClassTag]: A = application.injector.instanceOf[A]
}
