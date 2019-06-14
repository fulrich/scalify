package com.github.fulrich.scalify.installation

import java.time.Instant


case class InstallParameters(
  shop: String,
  timestamp: Instant
)

object InstallParameters {
  val ShopKey = "shop"
  val TimestampKey = "timestamp"

  def apply(shop: Option[String], timestamp: Option[Instant]): Option[InstallParameters] = for {
    unpackedShop <- shop
    unpackedTimestamp <- timestamp
  } yield InstallParameters(unpackedShop, unpackedTimestamp)
}
