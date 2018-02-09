package com.github.amazingdreams.mollie.objects

import play.api.libs.json._
import play.api.libs.functional.syntax._

object Subscription {
  import com.github.amazingdreams.mollie.utils.json.ReadUtils.readDoubleFromString

  implicit val subscriptionLinkReads = Json.reads[SubscriptionLinks]
  implicit val subscriptionReads: Reads[Subscription] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "customerId").read[String] and
    (JsPath \ "mode").read[String] and
    (JsPath \ "createdDatetime").read[String] and
    (JsPath \ "status").read[String] and
    (JsPath \ "amount").read[Either[String, BigDecimal]].map(_.fold(
      str => str.toDouble,
      bd => bd.toDouble
    )) and
    (JsPath \ "times").readNullable[Int] and
    (JsPath \ "interval").read[String] and
    (JsPath \ "description").read[String] and
    (JsPath \ "method").readNullable[String] and
    (JsPath \ "cancelledDatetime").readNullable[String] and
    (JsPath \ "links").read[SubscriptionLinks]
  )(Subscription.apply _)
}

case class Subscription(id: String,
                        customerId: String,
                        mode: String,
                        createdDatetime: String,
                        status: String,
                        amount: Double,
                        times: Option[Int],
                        interval: String,
                        description: String,
                        method: Option[String],
                        cancelledDatetime: Option[String],
                        links: SubscriptionLinks)

case class SubscriptionLinks(webhookUrl: Option[String])
