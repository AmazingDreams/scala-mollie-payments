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

    result.right.map { payment =>
      assert(payment.id != "")
      assert(payment.amount == 10d)
      assert(payment.links.paymentUrl != null)
    }
  }
}
