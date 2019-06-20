package com.github.fulrich.scalify

case class ShopifyConfiguration(
  apiKey: String,
  apiSecret: String,
  scopes: Seq[String] = Vector.empty
)
