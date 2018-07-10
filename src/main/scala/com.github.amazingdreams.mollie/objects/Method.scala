package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.requests.MollieResponse
import play.api.libs.json._

object Method {
  lazy implicit val methodImagesReads = Json.reads[MethodImages]
  lazy implicit val methodReads = Json.reads[Method]
}

case class Method(id: String,
                  description: String,
                  image: MethodImages)
  extends MollieResponse

case class MethodImages(size1x: String,
                        size2x: String)
