package com.github.fulrich.scalify.play.sbt.generators

import sbt._

object Generate {
  def apply(args: Seq[String], baseDirectory: File): Unit = args match {
    case Seq(GenerationTypes.ShopifyConf, args @ _*) => GenerateShopifyConf(baseDirectory)
    case _ => println(s"Unable to generate for arguments: $args")
  }
}
