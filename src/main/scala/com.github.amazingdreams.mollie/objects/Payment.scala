package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.objects.PaymentStatus.PaymentStatus
import com.github.amazingdreams.mollie.objects.SequenceType.SequenceType
import com.github.amazingdreams.mollie.requests.MollieResponse
import play.api.libs.json._

object Payment {

  implicit val paymentStatusReads = Reads.enumNameReads(PaymentStatus)
  implicit val recurringTypeReads = Reads.enumNameReads(SequenceType)

  implicit val paymentLinkReads = Json.reads[PaymentLink]
  implicit val paymentLinksReads = Json.reads[PaymentLinks]

  implicit val paymentReads = Json.reads[Payment]
}

case class Payment(id: String,
                   status: PaymentStatus,
                   amount: Amount,
                   description: String,
                   metadata: Option[Map[String, String]] = None,
                   profileId: Option[String] = None,
                   sequenceType: SequenceType,
                   redirectUrl: String,
                   webhookUrl: String,
                   _links: PaymentLinks)
  extends MollieResponse

case class PaymentLink(href: String,
                       `type`: String)

case class PaymentLinks(self: PaymentLink,
                        checkout: PaymentLink,
                        documentation: PaymentLink)

object SequenceType extends Enumeration {
  type SequenceType = Value

  val First = Value("first")
  val Recurring = Value("recurring")
  val OneOff = Value("oneoff")
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