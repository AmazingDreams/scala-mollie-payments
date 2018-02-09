package com.github.amazingdreams.mollie.requests
import com.github.amazingdreams.mollie.objects.Customer
import play.api.libs.json.Json

case class GetCustomerRequest(customerId: String) extends MollieRequest[Customer] {
  override def path = s"/customers/$customerId"
  override def responseReads = Json.reads[Customer]
}

