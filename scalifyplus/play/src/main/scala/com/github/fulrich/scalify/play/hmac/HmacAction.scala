package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.{Invalid, ShopifyHmac, Valid}
import com.github.fulrich.scalify.play.hmac.HmacAction._
import com.github.fulrich.scalify.serialization.url.QueryStringFromMap
import javax.inject.Inject
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}


class HmacAction @Inject()(val parser: BodyParsers.Default, configuration: ShopifyConfiguration)(implicit val executionContext: ExecutionContext)
  extends ActionRefiner[Request, HmacRequest] with ActionBuilder[HmacRequest, AnyContent] {

  def refine[A](request: Request[A]): Future[Either[Result, HmacRequest[A]]] =
    ShopifyHmac.validate(QueryStringFromMap(request.queryString))(configuration) match {
      case Some(Valid(payload)) => Future.successful(Right(new HmacRequest(payload, request)))
      case Some(Invalid) => Future.successful(InvalidHmac)
      case None => Future.successful(MissingHmac)
    }
}

object HmacAction {
  val HmacKey = "hmac"

  val MissingHmacMessage = "Must provide an HMAC query parameter."
  val MissingHmac = Left(UnprocessableEntity(MissingHmacMessage))

  val InvalidHmacMessage = "Invalid HMAC provided."
  val InvalidHmac = Left(Forbidden(InvalidHmacMessage))
}
