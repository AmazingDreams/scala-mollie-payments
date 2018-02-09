package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Customer
import com.github.amazingdreams.mollie.requests.RequestMethod.RequestMethod
import play.api.libs.json.{JsObject, Json, Reads}

case class CreateCustomerRequest(name: Option[String] = None,
                                 email: Option[String] = None,
                                 locale: Option[String] = None,
                                 metadata: Option[Map[String, String]] = None) extends MollieRequest[Customer] {
  override def path: String = "customers"
  override def method: RequestMethod = RequestMethod.POST
  override def postData: JsObject = Json.obj(
    "name" -> name,
    "email" -> email,
    "locale" -> locale,
    "metadata" -> metadata
  )
  override def responseReads = Json.reads[Customer]
  override def successResponseCode: Int = 201
}
