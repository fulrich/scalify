package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallConfirmation
import com.github.fulrich.scalify.play.hmac.HmacRequest
import javax.inject.Inject
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.{ActionRefiner, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}


class AuthorizeRequest[A](val parameters: InstallConfirmation, request: HmacRequest[A]) extends WrappedRequest[A](request)

class AuthorizeAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacRequest, AuthorizeRequest] {

  override protected def refine[A](request: HmacRequest[A]): Future[Either[Result, AuthorizeRequest[A]]] =
    Future.successful(
      InstallConfirmationBinding.bind(request)
        .map(parameters => new AuthorizeRequest(parameters, request))
        .badMap(errors => UnprocessableEntity(errors.toList.mkString("<br>")))
        .toEither
    )
}
