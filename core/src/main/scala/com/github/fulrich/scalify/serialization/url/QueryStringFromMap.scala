package com.github.fulrich.scalify.serialization.url

import io.lemonlabs.uri.QueryString

object QueryStringFromMap {
  def apply(queryMap: Map[String, Seq[String]]): QueryString =
    QueryString(
      queryMap.toVector.flatMap { case (paramKey, paramList) =>
        paramList.map {
          paramValue => (paramKey, Option(paramValue))
        }
      }
    )
}
