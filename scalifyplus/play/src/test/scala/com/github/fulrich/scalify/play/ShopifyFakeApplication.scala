package com.github.fulrich.scalify.play

import _root_.play.api.Application
import _root_.play.api.inject.bind
import _root_.play.api.inject.guice.GuiceApplicationBuilder
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._
import scala.reflect.ClassTag


trait ShopifyFakeApplication {
  val Configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value

  val fakeApplication: Application =
    GuiceApplicationBuilder().overrides(
      bind[ShopifyConfiguration].toInstance(Configuration)
    ).build()

  def inject[A : ClassTag]: A = fakeApplication.injector.instanceOf[A]
}
