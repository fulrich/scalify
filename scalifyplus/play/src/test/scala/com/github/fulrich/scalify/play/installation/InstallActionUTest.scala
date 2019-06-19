package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.installation.InstallParametersGenerator
import com.github.fulrich.scalify.hmac.ShopifyHmac
import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.ShopifyInjectedApplication
import com.github.fulrich.scalify.play.hmac.HmacAction
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsString, contentType, status, stubControllerComponents, _}
import com.github.fulrich.testcharged.generators._
import com.github.fulrich.scalify.serialization.url._

import scala.concurrent.Future


class InstallActionUTest extends FunSuite with Matchers with GuiceOneAppPerTest with ShopifyInjectedApplication {
  class TestController(hmacAction: HmacAction, installAction: InstallAction, cc: ControllerComponents) extends AbstractController(cc) {
    def install = hmacAction.andThen(installAction) { request =>
      Ok(request.parameters.shop + "|" + request.parameters.timestamp)
    }
  }

  trait Fixture {
    val configuration: ShopifyConfiguration = inject[ShopifyConfiguration]
    val controller = new TestController(inject[HmacAction], inject[InstallAction], stubControllerComponents())
    def parameters: String

    def request: Request[AnyContentAsEmpty.type] = {
      val hmac = ShopifyHmac.calculateHmac(parameters)(configuration)
      FakeRequest(GET, s"/install?hmac=$hmac&$parameters")
    }
  }


  test ("InstallAction will return an UnprocessableEntity error if no parameters are provided") { new Fixture {
    val parameters: String = "timestamp=1557768838"
    val result: Future[Result] = controller.install(request)

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  } }

  test ("InstallAction will return an UnprocessableEntity error if no timestamp is provided") { new Fixture {
    val parameters: String = "shop=store.shopify.com"
    val result: Future[Result] = controller.install(request)

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  } }

  test ("InstallAction will return an UnprocessableEntity error if the timestamp cannot be parsed") { new Fixture {
    val parameters: String = "shop=store.shopify.com&timestamp=art1557768838"
    val result: Future[Result] = controller.install(request)

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  } }

  test ("InstallAction will have the parsed Shop and Timestamp if they were valid") { new Fixture {
    val installParameters: InstallParameters = InstallParametersGenerator().value
    val parameters: String = unbind("", installParameters)
    val result: Future[Result] = controller.install(request)
    println(parameters)

    status(result) shouldBe OK
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include(s"${installParameters.shop}|${installParameters.timestamp}")
  } }
}
