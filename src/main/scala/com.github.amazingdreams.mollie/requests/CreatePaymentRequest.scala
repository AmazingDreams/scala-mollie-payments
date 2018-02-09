package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Payment
import com.github.amazingdreams.mollie.requests.RequestMethod.RequestMethod
import play.api.libs.json._

case class CreatePaymentRequest(amount: Double,
                                description: String,
                                redirectUrl: String,
                                webhookUrl: String,
                                locale: Option[String] = None,
                                metadata: Option[Map[String, String]] = None,
                                recurringType: Option[String] = None,
                                mandateId: Option[String] = None)
  extends MollieRequest {
  override def method: RequestMethod = RequestMethod.POST
  override def path: String = "payments"
  override def postData: JsObject = Json.obj(
    "amount" -> amount,
    "description" -> description,
    "redirectUrl" -> redirectUrl,
    "webhookUrl" -> webhookUrl,
    "locale" -> locale,
    "metadata" -> metadata,
    "recurringType" -> recurringType,
    "mandateId" -> mandateId
  )
  override def responseReads = Payment.paymentReads
  override def successResponseCode: Int = 201
}
