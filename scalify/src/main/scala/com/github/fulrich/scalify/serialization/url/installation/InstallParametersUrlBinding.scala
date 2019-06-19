package com.github.fulrich.scalify.serialization.url.installation

import java.time.Instant

import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.serialization.url._
import io.lemonlabs.uri.QueryString
import org.scalactic.Accumulation.withGood


object InstallParametersUrlBinding extends UrlBinding[InstallParameters] with UrlBindingDsl {
  override def bind(key: String, query: QueryString): Option[UrlBind[InstallParameters]] =
    Option(withGood(
      requiredBind[String](InstallParameters.ShopKey, query),
      requiredBind[Instant](InstallParameters.TimestampKey, query)
    )(InstallParameters.apply))

  override def unbind(key: String, parameters: InstallParameters): String =
    Vector(
      unbind(InstallParameters.ShopKey, parameters.shop),
      unbind(InstallParameters.TimestampKey, parameters.timestamp)
    ).sorted.mkString("&")
}
