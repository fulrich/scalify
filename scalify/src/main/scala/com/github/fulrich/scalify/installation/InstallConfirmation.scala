package com.github.fulrich.scalify.installation

import java.time.Instant


case class InstallConfirmation(
  shop: String,
  authorizationCode: String,
  nonce: String,
  timestamp: Instant) {

  def validateNonce(savedNonce: String): Boolean = nonce == savedNonce
  def validateNonce(savedNonce: Option[String]): Boolean = savedNonce.contains(nonce)
}

object InstallConfirmation {
  val ShopKey = "shop"
  val AuthorizationCodeKey = "code"
  val NonceKey = "state"
  val TimestampKey = "timestamp"
}
