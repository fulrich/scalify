package com.github.fulrich.scalify.play.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.play.hmac.HmacRequest
import com.github.fulrich.scalify.play.installation.InstallAction._
import javax.inject.Inject
import play.api.mvc.Results.UnprocessableEntity
import play.api.mvc.{ActionRefiner, Result, WrappedRequest}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


class InstallRequest[A](val parameters: InstallParameters, request: HmacRequest[A]) extends WrappedRequest[A](request)

class InstallAction @Inject()(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[HmacRequest, InstallRequest] {

  override protected def refine[A](request: HmacRequest[A]): Future[Either[Result, InstallRequest[A]]] =
    Future.successful(
      for {
        shop <- parseShop(request)
        timestamp <- parseTimestamp(request)
      } yield new InstallRequest(InstallParameters(shop, timestamp), request)
    )


  def parseShop[A](request: HmacRequest[A]): Either[Result, String] =
    request.getQueryString(InstallParameters.ShopKey) match {
      case Some(shop) => Right(shop)
      case None => MissingShop
    }

  def parseTimestamp[A](request: HmacRequest[A]): Either[Result, Instant] =
    request.getQueryString(InstallParameters.TimestampKey) match {
      case Some(timestampString) => parseInstant(timestampString)
      case None => MissingTimestamp
    }

  def parseInstant(timestampString: String): Either[Result, Instant] =
    Try(Instant.ofEpochSecond(timestampString.toLong)) match {
      case Success(timestampInstant) => Right(timestampInstant)
      case Failure(_) => InvalidTimestamp
    }
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
