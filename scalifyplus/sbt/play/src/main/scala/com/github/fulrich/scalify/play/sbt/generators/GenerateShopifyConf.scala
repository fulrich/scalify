package com.github.fulrich.scalify.play.sbt.generators

import sbt._
import com.github.fulrich.scalify.play.sbt.EnvironmentFile


object GenerateShopifyConf {
  def apply(baseDirectory: File): Unit = {
    val environmentFile = EnvironmentFile(baseDirectory)

    generateDevelopmentConf(environmentFile, baseDirectory)
    generateProductionConf(environmentFile, baseDirectory)
  }

  private def generateDevelopmentConf(environmentFile: EnvironmentFile, baseDirectory: File): Unit = {
    val developmentConf =  baseDirectory / "conf" / "development" / "shopify.conf"
    IO.write(
      developmentConf,
      shopifyConf(
        apiKey = "\"DEVELOPMENT_SHOPIFY_API_KEY\"",
        apiSecret = "\"DEVELOPMENT_SHOPIFY_API_SECRET\"",
        scopes = environmentFile.scopes
      )
    )
  }

  private def generateProductionConf(environmentFile: EnvironmentFile, baseDirectory: File): Unit = {
    val productionConf =  baseDirectory / "conf" / "production" / "shopify.conf"
    IO.write(
      productionConf,
      shopifyConf(
        apiKey = "${?SHOPIFY_API_KEY}",
        apiSecret = "${?SHOPIFY_API_SECRET}",
        scopes = environmentFile.scopes
      )
    )
  }

  private def shopifyConf(apiKey: String, apiSecret: String, scopes: Seq[String]): String = {
    s"""shopify {
       |  apiKey = $apiKey
       |  apiSecret = $apiSecret
       |  scopes = [${scopes.mkString(",")}]
       |}
       |""".stripMargin
  }
}
