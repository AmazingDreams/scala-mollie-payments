package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Amount
import org.scalatest.FunSuite

class CreatePaymentIdealRequestTest extends FunSuite {

  test("has proper postData") {
    val request = CreatePaymentIdealRequest(
      amount = Amount("EUR", "10.00"),
      description = "test",
      redirectUrl = "https://example.com/redirect",
      webhookUrl = "https://example.com/webhook",
      issuer = Some("ideal_ISSUER")
    )

    assert((request.postData \ "issuer").as[String] == "ideal_ISSUER")
  }
}
