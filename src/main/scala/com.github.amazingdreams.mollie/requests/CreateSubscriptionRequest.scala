package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Subscription
import play.api.libs.json.Json

case class CreateSubscriptionRequest(customerId: String,
                                     amount: Double,
                                     times: Option[Int] = None,
                                     interval: String,
                                     startDate: Option[String] = None,
                                     description: String,
                                     paymentMethod: Option[String] = None,
                                     webhookUrl: Option[String] = None)
  extends MollieRequest[Subscription] {
  override def method = RequestMethod.POST
  override def path = s"customers/$customerId/subscriptions"
  override def postData = Json.obj(
    "amount" -> amount,
    "times" -> times,
    "interval" -> interval,
    "startDate" -> startDate,
    "description" -> description,
    "method" -> paymentMethod,
    "webhookUrl" -> webhookUrl
  )
  override def responseReads = Subscription.subscriptionReads
  override def successResponseCode = 201
}
