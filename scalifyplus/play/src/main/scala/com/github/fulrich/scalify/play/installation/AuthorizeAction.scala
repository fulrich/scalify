package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.hmac.HmacWrappedRequest
import com.github.fulrich.scalify.serialization.url._
import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc.{ActionRefiner, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}


class AuthorizeRequest[A](val confirmation: AuthorizeConfirmation, request: HmacWrappedRequest[A]) extends WrappedRequest[A](request) {
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

class AuthorizeAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacWrappedRequest, AuthorizeRequest] {

  override protected def refine[A](request: HmacWrappedRequest[A]): Future[Either[Result, AuthorizeRequest[A]]] =
    Future.successful(
      bind[AuthorizeConfirmation](request.payload)
        .map(parameters => new AuthorizeRequest(parameters, request))
        .badMap(errors => UnprocessableEntity(errors.toList.mkString("<br>")))
        .toEither
    )
}
