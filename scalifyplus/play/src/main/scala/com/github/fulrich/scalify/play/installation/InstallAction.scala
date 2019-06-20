package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.hmac.HmacWrappedRequest
import com.github.fulrich.scalify.serialization.url._
import javax.inject.Inject
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.{ActionRefiner, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}


class InstallRequest[A](val parameters: InstallParameters, request: HmacWrappedRequest[A]) extends WrappedRequest[A](request)

class InstallAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacWrappedRequest, InstallRequest] {

  override protected def refine[A](request: HmacWrappedRequest[A]): Future[Either[Result, InstallRequest[A]]] =
    Future.successful(
      bind[InstallParameters](request.payload)
        .map(parameters => new InstallRequest(parameters, request))
        .badMap(errors => UnprocessableEntity(errors.toList.mkString("<br>")))
        .toEither
    )
}
