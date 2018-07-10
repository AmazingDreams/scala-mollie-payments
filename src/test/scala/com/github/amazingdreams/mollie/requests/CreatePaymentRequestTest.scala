package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.{Amount, SequenceType}
import org.scalatest.FunSuite
import play.api.libs.json.Json

class CreatePaymentRequestTest extends FunSuite {

  test("has correct path") {
    val request = CreatePaymentRequest(
      amount = Amount("EUR", "10.00"),
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )
    assert(request.path == "payments")
  }

  test("has correct params") {
    val request = CreatePaymentRequest(
      amount = Amount("EUR", "10.00"),
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    assert((request.postData \ "amount" \ "value").as[String] == "10.00")
    assert((request.postData \ "description").as[String] == "test")
    assert((request.postData \ "redirectUrl").as[String] == "http://example.com/redirect")
    assert((request.postData \ "webhookUrl").as[String] == "http://example.com/webhook")
  }

  test("can decode sample response") {
    val responseJson =
      """
      |{
      |    "resource": "payment",
      |    "id": "tr_7UhSN1zuXS",
      |    "mode": "test",
      |    "createdAt": "2018-03-20T09:13:37+00:00",
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
      |    "expiresAt": "2018-03-20T09:28:37+00:00",
      |    "details": null,
      |    "profileId": "pfl_QkEhN94Ba",
      |    "sequenceType": "oneoff",
      |    "redirectUrl": "https://webshop.example.org/order/12345/",
      |    "webhookUrl": "https://webshop.example.org/payments/webhook/",
      |    "_links": {
      |        "self": {
      |            "href": "https://api.mollie.com/v2/payments/tr_7UhSN1zuXS",
      |            "type": "application/json"
      |        },
      |        "checkout": {
      |            "href": "https://www.mollie.com/payscreen/select-method/7UhSN1zuXS",
      |            "type": "text/html"
      |        },
      |        "documentation": {
      |            "href": "https://docs.mollie.com/reference/v2/payments-api/create-payment",
      |            "type": "text/html"
      |        }
      |    }
      |}
      """.stripMargin

    val request = CreatePaymentRequest(
      amount = Amount("EUR", "10.00"),
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.amount.value == "10.00")
    assert(response._links.checkout.href.nonEmpty)
  }

  test("can decode sample response with recurringType first") {
    val responseJson =
      """
        |{
        |    "resource": "payment",
        |    "id": "tr_7UhSN1zuXS",
        |    "mode": "test",
        |    "createdAt": "2018-03-20T09:13:37+00:00",
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
        |    "expiresAt": "2018-03-20T09:28:37+00:00",
        |    "details": null,
        |    "profileId": "pfl_QkEhN94Ba",
        |    "sequenceType": "first",
        |    "redirectUrl": "https://webshop.example.org/order/12345/",
        |    "webhookUrl": "https://webshop.example.org/payments/webhook/",
        |    "_links": {
        |        "self": {
        |            "href": "https://api.mollie.com/v2/payments/tr_7UhSN1zuXS",
        |            "type": "application/json"
        |        },
        |        "checkout": {
        |            "href": "https://www.mollie.com/payscreen/select-method/7UhSN1zuXS",
        |            "type": "text/html"
        |        },
        |        "documentation": {
        |            "href": "https://docs.mollie.com/reference/v2/payments-api/create-payment",
        |            "type": "text/html"
        |        }
        |    }
        |}
      """.stripMargin

    val request = CreatePaymentRequest(
      amount = Amount("EUR", "10.00"),
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.amount.value == "10.00")
    assert(response._links.checkout.href.nonEmpty)
    assert(response.sequenceType == SequenceType.First)
  }
}
