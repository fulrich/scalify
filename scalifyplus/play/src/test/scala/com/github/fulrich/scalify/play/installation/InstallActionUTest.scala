package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.generators.installation.InstallParametersGenerator
import com.github.fulrich.scalify.hmac.ShopifyHmac
import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.installation.InstallationStubs._
import com.github.fulrich.scalify.serialization.url._
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsString, contentType, status, _}

import scala.concurrent.Future


class InstallActionUTest extends FunSuite with Matchers {
  val configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value
  def controllerComponents: ControllerComponents = stubControllerComponents()
  def controller = new AbstractController(controllerComponents) {
    def install = stubInstallActions(configuration)(controllerComponents).install { request =>
      Ok(request.parameters.shop + "|" + request.parameters.timestamp)
    }
  }

  def request(parameters: String): Request[AnyContentAsEmpty.type] = {
    val hmac = ShopifyHmac.calculateHmac(parameters)(configuration)
    FakeRequest(GET, s"/install?hmac=$hmac&$parameters")
  }


  test ("InstallAction will return an UnprocessableEntity error if no parameters are provided") {
    val parameters: String = "timestamp=1557768838"
    val result: Future[Result] = controller.install(request(parameters))

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  }

  test ("InstallAction will return an UnprocessableEntity error if no timestamp is provided") {
    val parameters: String = "shop=store.shopify.com"
    val result: Future[Result] = controller.install(request(parameters))

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  }

  test ("InstallAction will return an UnprocessableEntity error if the timestamp cannot be parsed") {
    val parameters: String = "shop=store.shopify.com&timestamp=art1557768838"
    val result: Future[Result] = controller.install(request(parameters))

    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
  }

  test ("InstallAction will have the parsed Shop and Timestamp if they were valid") {
    val installParameters: InstallParameters = InstallParametersGenerator().value
    val parameters: String = unbind(installParameters).toString.drop(1)
    val result: Future[Result] = controller.install(request(parameters))

    status(result) shouldBe OK
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include(s"${installParameters.shop}|${installParameters.timestamp}")
  }
}
