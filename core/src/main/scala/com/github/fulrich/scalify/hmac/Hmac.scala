package com.github.fulrich.scalify.hmac

import com.github.fulrich.scalify.ShopifyConfiguration
import io.lemonlabs.uri.QueryString


object Hmac extends ShopifyQueryString{
  def apply[A](hmac: Option[String], payload: Option[A])(implicit configuration: ShopifyConfiguration): Hmac[A] = {
    val optionalHmac = for {
      someHmac <- hmac
      somePayload <- payload
    } yield apply(someHmac, somePayload)

    optionalHmac getOrElse Invalid
  }

  def apply(hmac: String, payload: QueryString)(implicit configuration: ShopifyConfiguration): Hmac[QueryString] =
    apply(hmac, payload.toSortedString).map(_ => payload)

  def apply[A](hmac: String, payload: A)(implicit configuration: ShopifyConfiguration): Hmac[A] =
    if (ShopifyHmac.validate(hmac, payload.toString)) Valid(payload)
    else Invalid
}

sealed abstract class Hmac[+A] {
  def isValid: Boolean

  def isInvalid: Boolean = !isValid

  def get: A

  @inline final def getOrElse[B >: A](default: => B): B =
    if (isInvalid) default else this.get

  @inline final def map[B](f: A => B): Hmac[B] =
    if (isInvalid) Invalid else Valid(f(this.get))

  @inline final def flatMap[B](f: A => Hmac[B]): Hmac[B] =
    if (isInvalid) Invalid else f(this.get)

  @inline def toOption: Option[A] =
    if(isInvalid) None else Some(get)
}

final case class Valid[+A](value: A) extends Hmac[A] {
  def isValid: Boolean = true
  def get: A = value
}

case object Invalid extends Hmac[Nothing] {
  def isValid: Boolean = false
  def get = throw new NoSuchElementException("Invalid.get")
}
