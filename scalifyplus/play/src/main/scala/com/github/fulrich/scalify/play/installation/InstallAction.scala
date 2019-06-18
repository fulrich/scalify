package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.hmac.HmacRequest
import javax.inject.Inject
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.{ActionRefiner, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}


class InstallRequest[A](val parameters: InstallParameters, request: HmacRequest[A]) extends WrappedRequest[A](request)

class InstallAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacRequest, InstallRequest] {

  override protected def refine[A](request: HmacRequest[A]): Future[Either[Result, InstallRequest[A]]] =
    Future.successful(
      InstallParametersBinding.bind(request)
        .map(parameters => new InstallRequest(parameters, request))
        .badMap(errors => UnprocessableEntity(errors.toList.mkString("<br>")))
        .toEither
    )
}

object InstallAction {
  val HmacKey = "hmac"

  val MissingShopMessage = "A valid shop must be provided."
  val MissingShop = Left(UnprocessableEntity(MissingShopMessage))

  val MissingTimestampMessage = "A valid timestamp must be provided."
  val MissingTimestamp = Left(UnprocessableEntity(MissingTimestampMessage))

  val InvalidTimestampMessage = s"Could not parse key ${InstallParameters.TimestampKey} to an Instant."
  val InvalidTimestamp = Left(UnprocessableEntity(InvalidTimestampMessage))
}
