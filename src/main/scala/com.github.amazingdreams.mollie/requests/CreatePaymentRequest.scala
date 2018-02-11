package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Payment
import com.github.amazingdreams.mollie.requests.RequestMethod.RequestMethod
import play.api.libs.json._

trait CreatePaymentRequestTrait extends MollieRequest[Payment] {
  def amount: Double
  def description: String
  def redirectUrl: String
  def webhookUrl: String
  def locale: Option[String]
  def paymentMethod: Option[String]
  def metadata: Option[Map[String, String]]
  def recurringType: Option[String]
  def customerId: Option[String]
  def mandateId: Option[String]

  override def method: RequestMethod = RequestMethod.POST
  override def path: String = "payments"
  override def postData: JsObject = Json.obj(
    "amount" -> amount,
    "description" -> description,
    "redirectUrl" -> redirectUrl,
    "webhookUrl" -> webhookUrl,
    "locale" -> locale,
    "method" -> paymentMethod,
    "metadata" -> metadata,
    "recurringType" -> recurringType,
    "customerId" -> customerId,
    "mandateId" -> mandateId
  )
  override def responseReads = Payment.paymentReads
  override def successResponseCode: Int = 201
}

case class CreatePaymentRequest(amount: Double,
                                description: String,
                                redirectUrl: String,
                                webhookUrl: String,
                                locale: Option[String] = None,
                                paymentMethod: Option[String] = None,
                                metadata: Option[Map[String, String]] = None,
                                recurringType: Option[String] = None,
                                customerId: Option[String] = None,
                                mandateId: Option[String] = None)
  extends CreatePaymentRequestTrait

case class CreatePaymentIdealRequest(amount: Double,
                                     description: String,
                                     redirectUrl: String,
                                     webhookUrl: String,
                                     issuer: Option[String],
                                     locale: Option[String] = None,
                                     metadata: Option[Map[String, String]] = None,
                                     recurringType: Option[String] = None,
                                     customerId: Option[String] = None,
                                     mandateId: Option[String] = None)
  extends CreatePaymentRequestTrait {
  val paymentMethod = Some("ideal")

  override def postData: JsObject = super.postData.deepMerge(Json.obj(
    "issuer" -> issuer
  ))
}
