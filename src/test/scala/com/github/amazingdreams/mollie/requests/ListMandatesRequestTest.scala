package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Customer
import com.github.amazingdreams.mollie.testutils.MollieIntegrationSpec
import org.scalatest.FunSuite
import play.api.libs.json.Json

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ListMandatesRequestTest extends FunSuite {

  test("has correct path") {
    val request = ListMandatesRequest(
      customerId = "customer_id_123"
    )

    assert(request.path == "customers/customer_id_123/mandates")
  }

  test("has correct parameters") {
    val request = ListMandatesRequest(
      customerId = "customer_id_123",
      count = 10,
      offset = 15
    )

    val params = request.params.toMap
    assert(params("count") == "10")
    assert(params("offset") == "15")
  }

  test("can parse sample response") {
    val responseJson =
      """
        |{
        |    "totalCount": 2,
        |    "offset": 0,
        |    "count": 2,
        |    "data": [
        |        {
        |            "resource": "mandate",
        |            "id": "mdt_pO2m5jVgMa",
        |            "status": "valid",
        |            "method": "directdebit",
        |            "customerId": "cst_R6JLAuqEgm",
        |            "details": {
        |                "consumerName": "Hr E G H K\u00fcppers en\/of MW M.J. K\u00fcppers-Veeneman",
        |                "consumerAccount": "NL53INGB0618365937",
        |                "consumerBic": "INGBNL2A"
        |            },
        |            "createdDatetime": "2016-04-13T11:32:38.0Z"
        |        },
        |        {
        |            "resource": "mandate",
        |            "id": "mdt_qtUgejVgMN",
        |            "status": "valid",
        |            "method": "creditcard",
        |            "customerId": "cst_R6JLAuqEgm",
        |            "details": {
        |                "cardHolder": "John Doe",
        |                "cardNumber": "1234",
        |                "cardLabel": "Mastercard",
        |                "cardFingerprint": "fHB3CCKx9REkz8fPplT8N4nq",
        |                "cardExpiryDate": "2016-03-31"
        |            },
        |            "createdDatetime": "2016-04-13T11:32:38.0Z"
        |        }
        |    ]
        |}
      """.stripMargin

    val request = ListMandatesRequest(customerId = "")
    val response = request.responseReads.reads(Json.parse(responseJson)).get

    assert(response.totalCount == 2)
    assert(response.offset == 0)
    assert(response.count == 2)

    assert(response.data.size == 2)
    assert(response.data(0).id == "mdt_pO2m5jVgMa")
  }
}
