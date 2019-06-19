package com.github.fulrich.scalify.serialization.url.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.serialization.url._
import io.lemonlabs.uri.QueryString
import org.scalactic.Accumulation.withGood


object InstallParametersUrlBinding extends ObjectUrlBinding[InstallParameters] with UrlBindingDsl {
  override def bind(query: QueryString): UrlBind[InstallParameters] =
    withGood(
      bind[String](InstallParameters.ShopKey, query),
      bind[Instant](InstallParameters.TimestampKey, query)
    )(InstallParameters.apply)

  override def unbind(parameters: InstallParameters): String =
    unbindList(
      unbind(InstallParameters.ShopKey, parameters.shop),
      unbind(InstallParameters.TimestampKey, parameters.timestamp)
    )
}
