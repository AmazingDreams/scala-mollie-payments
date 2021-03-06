package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.objects.PaymentStatus.PaymentStatus
import com.github.amazingdreams.mollie.objects.RecurringType.RecurringType
import com.github.amazingdreams.mollie.requests.MollieResponse
import com.github.amazingdreams.mollie.utils.json.ReadUtils
import play.api.libs.json._
import play.api.libs.functional.syntax._

object Payment {
  import ReadUtils.readBigDecimalFromString

  implicit val paymentStatusReads = Reads.enumNameReads(PaymentStatus)
  implicit val recurringTypeReads = Reads.enumNameReads(RecurringType)
  implicit val paymentLinksReads = Json.reads[PaymentLinks]

  implicit val paymentReads: Reads[Payment] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "status").read[PaymentStatus] and
    (JsPath \ "amount").read[Either[String, BigDecimal]].map(_.fold(
      str => BigDecimal(str),
      bd => bd
    )) and
    (JsPath \ "description").read[String] and
    (JsPath \ "metadata").readNullable[Map[String, String]] and
    (JsPath \ "customerId").readNullable[String] and
    (JsPath \ "recurringType").readNullable[RecurringType] and
    (JsPath \ "subsciptionId").readNullable[String] and
    (JsPath \ "links").read[PaymentLinks]
  )(Payment.apply _)
}

case class Payment(id: String,
                   status: PaymentStatus,
                   amount: BigDecimal,
                   description: String,
                   metadata: Option[Map[String, String]] = None,
                   customerId: Option[String] = None,
                   recurringType: Option[RecurringType] = None,
                   subscriptionId: Option[String] = None,
                   links: PaymentLinks)
  extends MollieResponse

case class PaymentLinks(paymentUrl: Option[String],
                        redirectUrl: Option[String],
                        webhookUrl: Option[String])

object RecurringType extends Enumeration {
  type RecurringType = Value

  val First = Value("first")
  val Recurring = Value("recurring")
}

object PaymentStatus extends Enumeration {
  type PaymentStatus = Value

  val Cancelled = Value("cancelled")
  val ChargedBack = Value("charged_back")
  val Expired = Value("expired")
  val Failed = Value("failed")
  val Open = Value("open")
  val Paid = Value("paid")
  val Paidout = Value("paidout")
  val Pending = Value("pending")
  val Refunded = Value("refunded")
}