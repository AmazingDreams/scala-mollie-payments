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
        |    "createdAt": "2018-03-20T13:13:37+00:00",
        |    "amount": {
        |        "value": "10.00",
        |        "currency": "EUR"
        |    },
        |    "description": "My first payment",
        |    "method": null,
        |    "metadata": {
        |        "order_id": "12345"
        |    },
        |    "status": "open",
        |    "isCancelable": false,
        |    "expiresAt": "2018-03-20T13:28:37+00:00",
        |    "details": null,
        |    "profileId": "pfl_QkEhN94Ba",
        |    "sequenceType": "oneoff",
        |    "redirectUrl": "https://webshop.example.org/order/12345/",
        |    "webhookUrl": "https://webshop.example.org/payments/webhook/",
        |    "_links": {
        |        "self": {
        |            "href": "https://api.mollie.com/v2/payments/tr_WDqYK6vllg",
        |            "type": "application/hal+json"
        |        },
        |        "checkout": {
        |            "href": "https://www.mollie.com/payscreen/select-method/WDqYK6vllg",
        |            "type": "text/html"
        |        },
        |        "documentation": {
        |            "href": "https://docs.mollie.com/reference/v2/payments-api/get-payment",
        |            "type": "text/html"
        |        }
        |    }
        |}
      """.stripMargin

    val request = GetPaymentRequest(id = "payment_id_123")
    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.id == "tr_WDqYK6vllg")
    assert(response.metadata.get("order_id") == "12345")
  }
}
