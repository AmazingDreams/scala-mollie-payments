package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.requests.MollieResponse
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Payment {
  lazy implicit val readDoubleFromString = Reads[Either[String, BigDecimal]] {
    case JsNumber(a) => JsSuccess(Right(a))
    case JsString(a) => JsSuccess(Left(a))
    case _ => JsError("Type not supported")
  }

  lazy implicit val paymentLinksReads = Json.reads[PaymentLinks]
  lazy implicit val paymentReads: Reads[Payment] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "status").read[String] and
    (JsPath \ "amount").read[Either[String, BigDecimal]].map(_.fold(
      str => str.toDouble,
      bd => bd.toDouble
    )) and
    (JsPath \ "description").read[String] and
    (JsPath \ "metadata").readNullable[Map[String, String]] and
    (JsPath \ "links").read[PaymentLinks]
  )(Payment.apply _)
}

case class Payment(id: String,
                   status: String,
                   amount: Double,
                   description: String,
                   metadata: Option[Map[String, String]],
                   links: PaymentLinks)
  extends MollieResponse

case class PaymentLinks(paymentUrl: String,
                        redirectUrl: String,
                        webhookUrl: String)
