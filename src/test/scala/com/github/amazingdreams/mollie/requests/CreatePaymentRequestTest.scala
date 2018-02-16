package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.RecurringType
import org.scalatest.FunSuite
import play.api.libs.json.Json

class CreatePaymentRequestTest extends FunSuite {

  test("has correct path") {
    val request = CreatePaymentRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )
    assert(request.path == "payments")
  }

  test("has correct params") {
    val request = CreatePaymentRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    assert((request.postData \ "amount").as[Double] == 10d)
    assert((request.postData \ "description").as[String] == "test")
    assert((request.postData \ "redirectUrl").as[String] == "http://example.com/redirect")
    assert((request.postData \ "webhookUrl").as[String] == "http://example.com/webhook")
  }

  test("can decode sample response") {
    val responseJson =
      """
        |{
        |    "resource":        "payment",
        |    "id":              "tr_7UhSN1zuXS",
        |    "mode":            "test",
        |    "createdDatetime": "2018-02-08T16:46:53.0Z",
        |    "status":          "open",
        |    "expiryPeriod":    "PT15M",
        |    "amount":          "10.00",
        |    "description":     "My first payment",
        |    "metadata": {
        |        "order_id": "12345"
        |    },
        |    "locale": "nl",
        |    "profileId": "pfl_QkEhN94Ba",
        |    "links": {
        |        "paymentUrl":  "https://www.mollie.com/payscreen/select-method/7UhSN1zuXS",
        |        "redirectUrl": "https://webshop.example.org/order/12345/",
        |        "webhookUrl":  "https://webshop.example.org/payments/webhook/"
        |    }
        |}
      """.stripMargin

    val request = CreatePaymentRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.amount == 10d)
    assert(response.links.paymentUrl.isDefined)
  }

  test("can decode sample response with recurringType first") {
    val responseJson =
      """
        |{
        |    "resource":        "payment",
        |    "id":              "tr_7UhSN1zuXS",
        |    "mode":            "test",
        |    "createdDatetime": "2018-02-08T16:46:53.0Z",
        |    "status":          "open",
        |    "expiryPeriod":    "PT15M",
        |    "amount":          "10.00",
        |    "description":     "My first payment",
        |    "recurringType":   "first",
        |    "metadata": {
        |        "order_id": "12345"
        |    },
        |    "locale": "nl",
        |    "profileId": "pfl_QkEhN94Ba",
        |    "links": {
        |        "paymentUrl":  "https://www.mollie.com/payscreen/select-method/7UhSN1zuXS",
        |        "redirectUrl": "https://webshop.example.org/order/12345/",
        |        "webhookUrl":  "https://webshop.example.org/payments/webhook/"
        |    }
        |}
      """.stripMargin

    val request = CreatePaymentRequest(
      amount = 10d,
      description = "test",
      redirectUrl = "http://example.com/redirect",
      webhookUrl = "http://example.com/webhook"
    )

    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.amount == 10d)
    assert(response.links.paymentUrl.isDefined)
    assert(response.recurringType.contains(RecurringType.First))
  }
}
