package com.github.fulrich.scalify.play.sbt

import com.github.fulrich.scalify.play.sbt.generators.{Generate, GenerationTypes}
import sbt.Keys._
import sbt._
import complete.DefaultParsers._


object SbtPlayPlugin extends AutoPlugin {
  object autoImport {
    val shopifyGenerate = inputKey[Unit]("Used to generate Shopify helpers.")
  }

  import autoImport._

  override def trigger = allRequirements
  override def projectSettings: Seq[Setting[_]] = Seq(
    shopifyGenerate := generateTask.evaluated
  )

  def generateTask: Def.Initialize[InputTask[Any]] = Def.inputTask {
    Generate(
      spaceDelimited("<arg>").parsed,
      (baseDirectory in Compile).value
    )
  }
}
