package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.SequenceType.SequenceType
import com.github.amazingdreams.mollie.objects.{Amount, Payment, SequenceType}
import com.github.amazingdreams.mollie.requests.RequestMethod.RequestMethod
import play.api.libs.json._

trait CreatePaymentRequestTrait extends MollieRequest[Payment] {
  def amount: Amount
  def description: String
  def redirectUrl: String
  def webhookUrl: String
  def locale: Option[String]
  def paymentMethod: Option[String]
  def metadata: Option[Map[String, String]]
  def sequenceType: SequenceType
  def customerId: Option[String]
  def mandateId: Option[String]

  override def method: RequestMethod = RequestMethod.POST
  override def path: String = "payments"
  override def postData: JsObject = Json.obj(
    "amount" -> Json.obj(
      "currency" -> amount.currency,
      "value" -> amount.value
    ),
    "description" -> description,
    "redirectUrl" -> redirectUrl,
    "webhookUrl" -> webhookUrl,
    "locale" -> locale,
    "method" -> paymentMethod,
    "metadata" -> metadata,
    "sequenceType" -> sequenceType,
    "customerId" -> customerId,
    "mandateId" -> mandateId
  )
  override def responseReads = Payment.paymentReads
  override def successResponseCode: Int = 201
}

case class CreatePaymentRequest(amount: Amount,
                                description: String,
                                redirectUrl: String,
                                webhookUrl: String,
                                locale: Option[String] = None,
                                paymentMethod: Option[String] = None,
                                metadata: Option[Map[String, String]] = None,
                                sequenceType: SequenceType = SequenceType.OneOff,
                                customerId: Option[String] = None,
                                mandateId: Option[String] = None)
  extends CreatePaymentRequestTrait

case class CreatePaymentIdealRequest(amount: Amount,
                                     description: String,
                                     redirectUrl: String,
                                     webhookUrl: String,
                                     issuer: Option[String],
                                     locale: Option[String] = None,
                                     metadata: Option[Map[String, String]] = None,
                                     sequenceType: SequenceType = SequenceType.OneOff,
                                     customerId: Option[String] = None,
                                     mandateId: Option[String] = None)
  extends CreatePaymentRequestTrait {
  override val paymentMethod = Some("ideal")

  override def postData: JsObject = super.postData.deepMerge(Json.obj(
    "issuer" -> issuer
  ))
}
