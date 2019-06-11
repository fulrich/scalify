package com.github.fulrich.scalify.installation

import com.github.fulrich.scalify.generators.ShopifyConfigurationGenerator
import com.github.fulrich.scalify.generators.installation.{AuthorizeRedirectGenerator, InstallParametersGenerator}
import com.github.fulrich.testcharged.generators._
import org.scalatest.{FunSuite, Matchers}


class AuthorizeRedirectUTest extends FunSuite with Matchers {
  implicit val configuration = ShopifyConfigurationGenerator().value

  test("Can create an AuthorizeRedirect from InstallParameters and a redirectUri") {
    val parameters = InstallParametersGenerator().value
    val redirectUri = Generate.alpha.value

    val actual = AuthorizeRedirect(parameters, redirectUri)
    actual shouldBe AuthorizeRedirect(
      apiKey = configuration.apiKey,
      shop = parameters.shop,
      callbackUri = redirectUri,
      scopes = configuration.scopes,
      nonce = actual.nonce
    )
  }

  test("Generated the required Shopify URI for app installation") {
    val authorizeRedirect = AuthorizeRedirectGenerator().value

    val expectedUri = s"${AuthorizeRedirect.Protocol}://${authorizeRedirect.shop}${AuthorizeRedirect.AuthorizePath}" +
      s"?${AuthorizeRedirect.ClientIdKey}=${authorizeRedirect.apiKey}" +
      s"&${AuthorizeRedirect.ScopesKey}=${authorizeRedirect.scopes.mkString(AuthorizeRedirect.ScopesDelimiter)}" +
      s"&${AuthorizeRedirect.CallbackUriKey}=${authorizeRedirect.callbackUri}" +
      s"&${AuthorizeRedirect.NonceKey}=${authorizeRedirect.nonce}"

    authorizeRedirect.uri shouldBe expectedUri
  }

  test("Can create an AuthorizeRedirect from an optional InstallParameters and a redirectUri") {
    val parameters = InstallParametersGenerator().value
    val redirectUri = Generate.alpha.value

    AuthorizeRedirect(Some(parameters), redirectUri).isDefined shouldBe true
    AuthorizeRedirect(None, redirectUri) shouldBe None
  }

  test("Can create an AuthorizeRedirect from a valid Map of install parameters and a redirectUri") {
    val parameters = Map(
      InstallParameters.ShopKey -> Generate.alpha.value,
      InstallParameters.TimestampKey -> Generate.alpha.value
    )

    AuthorizeRedirect.fromMap(parameters, Generate.alpha.value).isDefined shouldBe true
    AuthorizeRedirect.fromMap(Map.empty, Generate.alpha.value) shouldBe None
  }

  test("Can create an AuthorizeRedirect from a valid SeqMap of install parameters and a redirectUri") {
    val parameters = Map(
      InstallParameters.ShopKey -> Vector(Generate.alpha.value),
      InstallParameters.TimestampKey -> Vector(Generate.alpha.value)
    )

    AuthorizeRedirect.fromSeqMap(parameters, Generate.alpha.value).isDefined shouldBe true
    AuthorizeRedirect.fromSeqMap(Map.empty, Generate.alpha.value) shouldBe None
  }
}
