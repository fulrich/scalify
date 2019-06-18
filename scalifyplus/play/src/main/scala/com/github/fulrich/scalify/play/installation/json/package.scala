package com.github.fulrich.scalify.play.installation

import com.github.fulrich.scalify.installation.TokenRequest
import play.api.libs.json.Format


package object json {
  implicit val tokenRequestJsonFormat:Format[TokenRequest] = TokenRequestJson.Format
}
