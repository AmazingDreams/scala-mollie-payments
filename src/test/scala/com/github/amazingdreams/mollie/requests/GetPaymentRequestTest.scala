package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Payment
import com.github.amazingdreams.mollie.testutils.MollieIntegrationSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class GetPaymentRequestTest extends MollieIntegrationSpec {

  behavior of "GetPaymentRequest"

  it should "Get a payment" in {
    // First create the payment
    val createResult = Await.result(mollieClient.request(CreatePaymentRequest(
      amount = 10d,
      description = "test payment",
      redirectUrl = "https://example.com/redirect",
      webhookUrl = "https://example.com/webhook"
    )), Duration.Inf)

    assert(createResult.isRight)

    createResult.right.map { createPayment =>
      val getResult = Await.result(mollieClient.request(GetPaymentRequest(
        id = createPayment.id
      )), Duration.Inf)

      assert(getResult.isRight)

      getResult.right.map { getPayment =>
        assert(getPayment.id == createPayment.id)
      }
    }
  }
}
