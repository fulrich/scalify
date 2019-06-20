package com.github.fulrich.scalify.hmac

import io.lemonlabs.uri.QueryString


object ShopifyQueryString {
  val QuerySeparator = 1

  def sorted(query: QueryString): QueryString =
    QueryString(
      query.params.sortBy { case (key, _) => key }
    )

  def toSortedString(query: QueryString): String = sorted(query).toString.drop(QuerySeparator)
}

trait ShopifyQueryString {
  implicit class ShopifyQueryStringOps(query: QueryString) {
    def sorted: QueryString = ShopifyQueryString.sorted(query)
    def toSortedString: String = ShopifyQueryString.toSortedString(query)
  }
}
