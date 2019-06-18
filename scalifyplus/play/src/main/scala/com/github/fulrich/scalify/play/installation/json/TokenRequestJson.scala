package com.github.fulrich.scalify.play.installation.json

import com.github.fulrich.scalify.installation.TokenRequest
import play.api.libs.json._
import play.api.libs.functional.syntax._


object TokenRequestJson {
  val Format: Format[TokenRequest] = (
    (JsPath \ TokenRequest.ApiKeyKey).format[String] and
    (JsPath \ TokenRequest.ApiSecretKey).format[String] and
    (JsPath \ TokenRequest.AuthorizationCodeKey).format[String]
  )(TokenRequest.apply, unlift(TokenRequest.unapply))
}
