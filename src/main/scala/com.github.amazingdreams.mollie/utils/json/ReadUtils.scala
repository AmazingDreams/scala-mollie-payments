package com.github.amazingdreams.mollie.utils.json

import play.api.libs.json._

object ReadUtils {
  lazy implicit val readDoubleFromString = Reads[Either[String, BigDecimal]] {
    case JsNumber(a) => JsSuccess(Right(a))
    case JsString(a) => JsSuccess(Left(a))
    case _ => JsError("Type not supported")
  }
}
