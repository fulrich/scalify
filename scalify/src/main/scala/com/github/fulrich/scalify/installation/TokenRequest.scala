package com.github.fulrich.scalify.installation

import com.github.fulrich.scalify.ShopifyConfiguration


case class TokenRequest(
  apiKey: String,
  apiSecret: String,
  authorizationCode: String
)

object TokenRequest {
  val ApiKeyKey: String = "client_id"
  val ApiSecretKey: String = "client_secret"
  val AuthorizationCodeKey: String = "code"

  def apply(confirmation: AuthorizeConfirmation)(implicit configuration: ShopifyConfiguration): TokenRequest =
    TokenRequest(
      apiKey = configuration.apiKey,
      apiSecret = configuration.apiSecret,
      authorizationCode = confirmation.authorizationCode
    )
}
