package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.AuthorizeConfirmation
import com.github.fulrich.scalify.play.hmac.HmacRequest
import com.github.fulrich.scalify.serialization.url._
import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc.{ActionRefiner, Result}

import scala.concurrent.{ExecutionContext, Future}


class AuthorizeAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacRequest, AuthorizeRequest] {

  override protected def refine[A](request: HmacRequest[A]): Future[Either[Result, AuthorizeRequest[A]]] =
    Future.successful(
      bind[AuthorizeConfirmation](request.payload)
        .map(parameters => new AuthorizeRequest(parameters, request))
        .badMap(errors => UnprocessableEntity(errors.toList.mkString("<br>")))
        .toEither
    )
}
