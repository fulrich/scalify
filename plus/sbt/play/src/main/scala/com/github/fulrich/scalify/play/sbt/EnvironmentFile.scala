package com.github.fulrich.scalify.play.sbt

import java.io.File

import scala.io.Source

case class EnvironmentFile(baseDirectory: File) {
  private val RawValues = EnvironmentFile.load(baseDirectory)

  def scopes: Seq[String] = RawValues.get(EnvironmentFile.Scopes) match {
    case Some(scopes) => scopes.split(',').toVector
    case None => Vector.empty
  }
}

object EnvironmentFile {
  val Filename = ".env"

  val Scopes = "SCOPES"

  def load(baseDirectory: File ): Map[String, String] = {
    val envFile = new File(baseDirectory, Filename)
    val envFileSource = Source.fromFile(envFile)
    val envFileLines = envFileSource.getLines.toVector
    envFileSource.close()

    envFileLines
      .map(_.split('='))
      .collect { case Array(first: String, second: String) => first -> second }
      .toMap
  }
}
