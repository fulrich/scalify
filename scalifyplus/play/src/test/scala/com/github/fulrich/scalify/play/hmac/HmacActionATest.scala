package shopify.play

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.ShopifyHmac
import com.github.fulrich.scalify.play.ShopifyInjectedApplication
import com.github.fulrich.scalify.play.hmac.HmacAction
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future


class HmacActionATest extends FunSuite with Matchers with GuiceOneAppPerTest with ShopifyInjectedApplication {
  class TestController(hmacAction: HmacAction, cc: ControllerComponents) extends AbstractController(cc) {
    def testAction: Action[AnyContent] = hmacAction { request =>
      Ok(request.getQueryString("shop").getOrElse("Error"))
    }
  }

  test ("HmacAction will return an UnprocessableEntity error if no HMAC is provided") {
    val controller = new TestController(inject[HmacAction], stubControllerComponents())
    val parameters = "shop=store.myshopify.com&timestamp=1557768838"
    val request = FakeRequest(GET, s"/install?$parameters")

    val result: Future[Result] = controller.testAction(request)
    status(result) shouldBe UNPROCESSABLE_ENTITY
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include(HmacAction.MissingHmacMessage)
  }

  test("Validate HMAC will return a 403 if the HMAC is invalid") {
    val controller = new TestController(inject[HmacAction], stubControllerComponents())
    val parameters = "shop=store.myshopify.com&timestamp=1557768838"
    val invalidHmac = Generate.alpha.value
    val request = FakeRequest(GET, s"/install?hmac=$invalidHmac&$parameters")

    val result: Future[Result] = controller.testAction(request)
    status(result) shouldBe FORBIDDEN
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include(HmacAction.InvalidHmacMessage)
  }

  test("Validate HMAC will run the action if the HMAC is valid") {
    val configuration = inject[ShopifyConfiguration]
    val controller = new TestController(inject[HmacAction], stubControllerComponents())

    val parameters = "shop=store.myshopify.com&timestamp=1557768838"
    val validHmac = ShopifyHmac.calculateHmac(parameters)(configuration)
    val request = FakeRequest(GET, s"/install?hmac=$validHmac&$parameters")

    val result: Future[Result] = controller.testAction(request)
    status(result) shouldBe OK
    contentType(result) shouldBe Some("text/plain")
    contentAsString(result) should include("store.myshopify.com")
  }
}
