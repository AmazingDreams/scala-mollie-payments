package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.requests.MollieResponse
import com.github.amazingdreams.mollie.utils.json.ReadUtils
import play.api.libs.json.Reads
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Method {
  import ReadUtils.readBigDecimalFromString

  lazy implicit val methodAmountReads: Reads[MethodAmounts] = (
    (JsPath \ "minimum").read[Either[String, BigDecimal]].map(_.fold(
      str => BigDecimal(str),
      bd => bd
    )) and
    (JsPath \ "maximum").read[Either[String, BigDecimal]].map(_.fold(
      str => BigDecimal(str),
      bd => bd
    ))
  )(MethodAmounts.apply _)
  lazy implicit val methodImagesReads = Json.reads[MethodImages]
  lazy implicit val methodReads = Json.reads[Method]
}

case class Method(id: String,
                  description: String,
                  amount: MethodAmounts) extends MollieResponse

case class MethodAmounts(minimum: BigDecimal,
                         maximum: BigDecimal)

case class MethodImages(normal: String,
                        bigger: String)
