package com.github.fulrich.scalify.play.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.{Invalid, Valid}
import play.api.mvc.Results.Forbidden
import play.api.mvc.{Action, BodyParser, Request, Result}

import scala.concurrent.{ExecutionContext, Future}


case class ValidateHmac[A](action: Action[A], configuration: ShopifyConfiguration) extends Action[A] {
  override def parser: BodyParser[A] = action.parser
  override def executionContext: ExecutionContext = action.executionContext

  def apply(request: Request[A]): Future[Result] = {
    HmacRequest.forQueryString(request, configuration) match {
      case Valid(validRequest) => action(validRequest)
      case Invalid => Future.successful(Forbidden("The request failed HMAC validation."))
    }
  }
}
