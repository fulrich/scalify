package com.github.fulrich.scalify.play

import _root_.play.api.Application
import _root_.play.api.inject.bind
import _root_.play.api.inject.guice.GuiceApplicationBuilder
import _root_.play.api.test.{HasApp, Injecting}
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.testcharged.generators._
import org.scalatest.TestSuite
import org.scalatestplus.play.FakeApplicationFactory


trait ShopifyInjectedApplication extends FakeApplicationFactory with Injecting { self: TestSuite with HasApp =>
  val Configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value

  override def fakeApplication(): Application =
    GuiceApplicationBuilder().overrides(
      bind[ShopifyConfiguration].toInstance(Configuration)
    ).build()
}
