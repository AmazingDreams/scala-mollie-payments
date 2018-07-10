package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Amount
import org.scalatest.FunSuite
import play.api.libs.json.Json

class ListMethodsRequestTest extends FunSuite {

  test("has correct path") {
    val request = ListMethodsRequest()

    assert(request.path == "methods")
  }

  test("has correct parameters") {
    val request = ListMethodsRequest(
      amount = Some(Amount(value = "10.00", currency = "USD"))
    )
    val paramsMap = request.params.toMap

    assert(paramsMap("amount[value]") == "10.00")
    assert(paramsMap("amount[currency]") == "USD")
  }

  test("can decode sample response") {
    val responseJson =
      """
        |{
        |    "count": 13,
        |    "_embedded": {
        |        "methods": [
        |            {
        |                 "resource": "method",
        |                 "id": "ideal",
        |                 "description": "iDEAL",
        |                 "image": {
        |                     "size1x": "https://mollie.com/images/payscreen/methods/ideal.png",
        |                     "size2x": "https://mollie.com/images/payscreen/methods/ideal%402x.png"
        |                 },
        |                 "_links": {
        |                     "self": {
        |                         "href": "https://api.mollie.com/v2/methods/ideal",
        |                         "type": "application/hal+json"
        |                     },
        |                     "documentation": {
        |                         "href": "https://mollie.com/en/docs/reference/methods/get",
        |                         "type": "text/html"
        |                     }
        |                 }
        |            }
        |        ]
        |    },
        |    "_links": {
        |        "self": {
        |            "href": "https://api.mollie.com/v2/methods",
        |            "type": "application/hal+json"
        |        },
        |        "documentation": {
        |            "href": "https://docs.mollie.com/reference/v2/methods-api/list-methods",
        |            "type": "text/html"
        |        }
        |    }
        |}
      """.stripMargin

    val request = ListMethodsRequest()
    val maybeResponse = request.responseReads.reads(Json.parse(responseJson))

    assert(maybeResponse.asOpt.isDefined)

    maybeResponse.map { response =>
      assert(response.count == 13)

      assert(response.methods.size === 1)

      assert(response.methods(0).id == "ideal")
      assert(response.methods(0).description == "iDEAL")
    }
  }
}
