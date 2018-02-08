package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Payment

case class GetPaymentRequest(id: String) extends MollieRequest {
  override def path: String = s"/payments/$id"
  override def responseReads = Payment.paymentReads
}
