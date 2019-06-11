package shopify.play

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.hmac.ShopifyHmac
import com.github.fulrich.scalify.play.hmac.ValidateHmac
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test.{FakeRequest, _}

import scala.concurrent.Future


class ValidateHmacUTest extends FunSuite with Matchers {
  val configuration: ShopifyConfiguration = ShopifyConfigurationGenerator().value
  val testAction: Action[AnyContent] = new TestController(Helpers.stubControllerComponents()).testAction
  class TestController(cc: ControllerComponents) extends AbstractController(cc) {
    def testAction: Action[AnyContent] = Action { request: Request[AnyContent] =>
      Ok(request.getQueryString("shop").getOrElse("Error"))
    }
  }

  test("Validate HMAC will return a 403 if the HMAC is invalid") {
    val parameters = "shop=fredsdevstore.myshopify.com&timestamp=1557768838"
    val invalidHmac = Generate.alpha.value
    val request = FakeRequest(GET, s"/install?hmac=$invalidHmac&$parameters")

    val result: Future[Result] = ValidateHmac(testAction, configuration)(request)
    status(result) shouldBe FORBIDDEN
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include("The request failed HMAC validation")
  }

  test("Validate HMAC will run the action if the HMAC is valid") {
    val parameters = "shop=fredsdevstore.myshopify.com&timestamp=1557768838"
    val validHmac = ShopifyHmac.calculateHmac(parameters)(configuration)
    val request = FakeRequest(GET, s"/install?hmac=$validHmac&$parameters")

    val result: Future[Result] = ValidateHmac(testAction, configuration)(request)
    status(result) shouldBe OK
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include("fredsdevstore.myshopify.com")
  }
}
