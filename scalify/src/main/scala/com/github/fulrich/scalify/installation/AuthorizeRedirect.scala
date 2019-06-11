package com.github.fulrich.scalify.installation

import java.util.UUID

import com.github.fulrich.scalify.ShopifyConfiguration
import io.lemonlabs.uri.Url


case class AuthorizeRedirect(
  apiKey: String,
  shop: String,
  callbackUri: String,
  scopes: Seq[String],
  nonce: String = UUID.randomUUID.toString) {

  lazy val uri: String =
    Url(scheme = AuthorizeRedirect.Protocol, host = shop, path = AuthorizeRedirect.AuthorizePath)
      .addParam(AuthorizeRedirect.ClientIdKey -> apiKey)
      .addParam(AuthorizeRedirect.ScopesKey -> scopes.mkString(AuthorizeRedirect.ScopesDelimiter))
      .addParam(AuthorizeRedirect.CallbackUriKey -> callbackUri)
      .addParam(AuthorizeRedirect.NonceKey -> nonce)
      .toString
}

object AuthorizeRedirect {
  val Protocol = "https"
  val AuthorizePath = "/admin/oauth/authorize"

  val ClientIdKey = "client_id"
  val ScopesKey = "scope"
  val CallbackUriKey = "redirect_uri"
  val NonceKey = "state"
  val ScopesDelimiter = ","

  def apply(parameters: InstallParameters, redirectUri: String)
           (implicit configuration: ShopifyConfiguration): AuthorizeRedirect =
    AuthorizeRedirect(
      apiKey = configuration.apiKey,
      shop = parameters.shop,
      callbackUri = redirectUri,
      scopes = configuration.scopes
    )

  def apply(parameters: Option[InstallParameters], redirectUri: String)
           (implicit configuration: ShopifyConfiguration): Option[AuthorizeRedirect] =
    parameters.map(apply(_, redirectUri))

  def fromMap(parameters: Map[String, String], redirectUri: String)
             (implicit configuration: ShopifyConfiguration): Option[AuthorizeRedirect] =
    InstallParameters.fromMap(parameters).map { installParameters => apply(installParameters, redirectUri) }

  def fromSeqMap(parameters: Map[String, Seq[String]], redirectUri: String)
                (implicit configuration: ShopifyConfiguration): Option[AuthorizeRedirect] =
    InstallParameters.fromSeqMap(parameters).map { installParameters => apply(installParameters, redirectUri) }
}
