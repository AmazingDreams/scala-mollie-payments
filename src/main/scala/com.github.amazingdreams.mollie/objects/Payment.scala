package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.requests.MollieResponse
import com.github.amazingdreams.mollie.utils.json.ReadUtils
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Payment {
  import ReadUtils.readBigDecimalFromString

  lazy implicit val paymentLinksReads = Json.reads[PaymentLinks]
  lazy implicit val paymentReads: Reads[Payment] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "status").read[String] and
    (JsPath \ "amount").read[Either[String, BigDecimal]].map(_.fold(
      str => BigDecimal(str),
      bd => bd
    )) and
    (JsPath \ "description").read[String] and
    (JsPath \ "metadata").readNullable[Map[String, String]] and
    (JsPath \ "customerId").readNullable[String] and
    (JsPath \ "recurringType").readNullable[String] and
    (JsPath \ "subsciptionId").readNullable[String] and
    (JsPath \ "links").read[PaymentLinks]
  )(Payment.apply _)
}

case class Payment(id: String,
                   status: String,
                   amount: BigDecimal,
                   description: String,
                   metadata: Option[Map[String, String]] = None,
                   customerId: Option[String] = None,
                   recurringType: Option[String] = None,
                   subscriptionId: Option[String] = None,
                   links: PaymentLinks)
  extends MollieResponse

case class PaymentLinks(paymentUrl: Option[String],
                        redirectUrl: String,
                        webhookUrl: String)
