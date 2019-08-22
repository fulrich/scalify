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

import scala.reflect.ClassTag


trait ShopifyInjectedApplication extends FakeApplicationFactory with Injecting { self: TestSuite with HasApp =>
  val Configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value

  private def applicationBuilder: GuiceApplicationBuilder =
    GuiceApplicationBuilder().overrides(bind[ShopifyConfiguration].toInstance(Configuration))

  override def fakeApplication(): Application = applicationBuilder.build()

  def customInject[A : ClassTag](customize: GuiceApplicationBuilder => GuiceApplicationBuilder): A =
    customize(applicationBuilder).build().injector.instanceOf[A]
}
