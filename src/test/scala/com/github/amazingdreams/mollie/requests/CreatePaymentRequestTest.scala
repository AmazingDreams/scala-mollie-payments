package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Payment
import com.github.amazingdreams.mollie.testutils.MollieIntegrationSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class CreatePaymentRequestTest extends MollieIntegrationSpec {

  behavior of "CreatePaymentRequest"

  it should "create a payment" in {
    val result = Await.result(mollieClient.request(CreatePaymentRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )), Duration.Inf)

    if (result.isLeft) {
      result.left.map { errors =>
        println(errors)
      }
    }

    assert(result.isRight)

    result.right.map {
      case response: Payment =>
        assert(response.id != "")
        assert(response.amount == 10d)
        assert(response.links.paymentUrl != null)
    }
  }
}
