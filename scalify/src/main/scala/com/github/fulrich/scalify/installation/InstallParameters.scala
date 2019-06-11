package com.github.fulrich.scalify.installation


case class InstallParameters(
  shop: String,
  timestamp: String
)

object InstallParameters {
  val ShopKey = "shop"
  val TimestampKey = "timestamp"

  def apply(shop: Option[String], timestamp: Option[String]): Option[InstallParameters] = for {
    unpackedShop <- shop
    unpackedTimestamp <- timestamp
  } yield InstallParameters(unpackedShop, unpackedTimestamp)

  def fromMap(parameters: Map[String, String]): Option[InstallParameters] =
    apply(shop = parameters.get(ShopKey), timestamp = parameters.get(TimestampKey))

  def fromSeqMap(parameters: Map[String, Seq[String]]): Option[InstallParameters] =
    apply(
      shop = parameters.get(ShopKey).flatMap(_.headOption),
      timestamp = parameters.get(TimestampKey).flatMap(_.headOption)
    )
}
