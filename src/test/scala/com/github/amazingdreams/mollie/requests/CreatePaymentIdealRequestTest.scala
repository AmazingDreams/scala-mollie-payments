package com.github.amazingdreams.mollie.requests

import org.scalatest.FunSuite

class CreatePaymentIdealRequestTest extends FunSuite {

  test("has proper postData") {
    val request = CreatePaymentIdealRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "https://example.com/redirect",
      webhookUrl = "https://example.com/webhook",
      issuer = Some("ideal_ISSUER")
    )

    assert((request.postData \ "issuer").as[String] == "ideal_ISSUER")
  }
}
