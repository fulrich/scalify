package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.{Hmac, Invalid, Valid}
import com.github.fulrich.scalify.play.hmac.HmacAction._
import javax.inject.Inject
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}


class HmacRequest[A](hmac: String, payload: String, request: Request[A]) extends WrappedRequest[A](request)

class HmacAction @Inject()(val parser: BodyParsers.Default, configuration: ShopifyConfiguration)(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[Request, HmacRequest] with ActionBuilder[HmacRequest, AnyContent] {

  def refine[A](request: Request[A]): Future[Either[Result, HmacRequest[A]]] =
    request.getQueryString("hmac") match {
      case None => Future.successful(MissingHmac)
      case Some(hmacValue) => validateHmac[A](request, hmacValue)
    }

  def queryStringWithoutHmac(request: Request[_]): String =
    request.queryString.filterKeys(_ != HmacKey)
      .mapValues(_.mkString(","))
      .toSeq
      .map { keyValueTuple => s"${keyValueTuple._1}=${keyValueTuple._2}" }
      .sorted
      .mkString("&")

  def validateHmac[A](request: Request[A], hmacValue: String): Future[Either[Result, HmacRequest[A]]] =
    Hmac(hmacValue, queryStringWithoutHmac(request))(configuration) match {
      case Invalid => Future.successful(InvalidHmac)
      case Valid(payload) => Future.successful(Right(new HmacRequest(hmacValue, payload, request)))
    }
}

object HmacAction {
  val HmacKey = "hmac"

  val MissingHmacMessage = "Must provide an HMAC query parameter."
  val MissingHmac = Left(UnprocessableEntity(MissingHmacMessage))

  val InvalidHmacMessage = "Invalid HMAC provided."
  val InvalidHmac = Left(Forbidden(InvalidHmacMessage))
}
