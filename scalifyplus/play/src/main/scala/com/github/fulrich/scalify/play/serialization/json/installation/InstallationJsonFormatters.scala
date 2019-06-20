package com.github.fulrich.scalify.play.serialization.json.installation

import com.github.fulrich.scalify.installation.TokenRequest
import play.api.libs.json.Format


trait InstallationJsonFormatters {
  implicit val tokenRequestJsonFormat: Format[TokenRequest] = TokenRequestJson.Format
}
