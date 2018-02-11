package com.github.amazingdreams.mollie.requests

import org.scalatest.FunSuite
import play.api.libs.json.Json

class GetPaymentRequestTest extends FunSuite {

  test("has correct path") {
    val request = GetPaymentRequest(id = "payment_id_123")

    assert(request.path == "payments/payment_id_123")
  }

  test("can parse response") {
    val responseJson =
      """
        |{
        |    "resource": "payment",
        |    "id": "tr_WDqYK6vllg",
        |    "mode": "test",
        |    "createdDatetime": "2018-02-08T16:37:34.0Z",
        |    "status": "paid",
        |    "paidDatetime": "2018-02-08T16:42:17.0Z",
        |    "amount": "35.07",
        |    "description": "Order 33",
        |    "method": "ideal",
        |    "metadata": {
        |        "order_id": "33"
        |    },
        |    "details": {
        |        "consumerName": "Hr E G H K\u00fcppers en\/of MW M.J. K\u00fcppers-Veeneman",
        |        "consumerAccount": "NL53INGB0618365937",
        |        "consumerBic": "INGBNL2A"
        |    },
        |    "locale": "nl",
        |    "profileId": "pfl_QkEhN94Ba",
        |    "links": {
        |        "webhookUrl": "https://webshop.example.org/payments/webhook",
        |        "redirectUrl": "https://webshop.example.org/order/33/"
        |    }
        |}
      """.stripMargin

    val request = GetPaymentRequest(id = "payment_id_123")
    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.id == "tr_WDqYK6vllg")
    assert(response.metadata.get("order_id") == "33")
  }
}
