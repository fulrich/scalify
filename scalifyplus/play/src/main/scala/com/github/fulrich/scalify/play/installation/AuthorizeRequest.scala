package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.hmac.HmacRequest
import play.api.mvc.Results.Forbidden
import play.api.mvc.{Result, WrappedRequest}

import scala.concurrent.Future


class AuthorizeRequest[A](val confirmation: AuthorizeConfirmation, request: HmacRequest[A]) extends WrappedRequest[A](request) {
  class NonceValidation(nonce: Option[String]) {
    def apply(block: AuthorizeConfirmation => Result): Result =
      runNonceBlock(
        default = Forbidden("Could not validate the provided nonce."),
        block = block
      )

    def async(block: AuthorizeConfirmation => Future[Result]): Future[Result] =
      runNonceBlock(
        default = Future.successful(Forbidden("Could not validate the provided nonce.")),
        block = block
      )

    private def runNonceBlock[B](default: B, block: AuthorizeConfirmation => B): B =
      if (confirmation.validateNonce(nonce)) block(confirmation) else default
  }

  def withValidNonce(nonce: Option[String]): NonceValidation = new NonceValidation(nonce)
  def withValidNonce(nonce: String): NonceValidation = new NonceValidation(Some(nonce))
}
