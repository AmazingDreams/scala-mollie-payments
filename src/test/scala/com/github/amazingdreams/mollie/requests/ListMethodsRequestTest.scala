package com.github.amazingdreams.mollie.requests

import org.scalatest.FunSuite
import play.api.libs.json.Json

class ListMethodsRequestTest extends FunSuite {

  test("has correct path") {
    val request = ListMethodsRequest()

    assert(request.path == "methods")
  }

  test("has correct parameters") {
    val request = ListMethodsRequest(
      count = 10,
      offset = 25
    )
    val paramsMap = request.params.toMap

    assert(paramsMap("count") == "10")
    assert(paramsMap("offset") == "25")
  }

  test("can decode sample response") {
    val responseJson =
      """
        |{
        |    "totalCount": 2,
        |    "offset": 0,
        |    "count": 2,
        |    "data": [
        |        {
        |            "resource": "method",
        |            "id": "ideal",
        |            "description": "iDEAL",
        |            "amount": {
        |                "minimum": "0.53",
        |                "maximum": "50000.00"
        |            },
        |            "image": {
        |                "normal": "https://www.mollie.com/images/payscreen/methods/ideal.png",
        |                "bigger": "https://www.mollie.com/images/payscreen/methods/ideal%402x.png"
        |            }
        |        },
        |        {
        |            "resource": "method",
        |            "id": "paypal",
        |            "description": "PayPal",
        |            "amount": {
        |                "minimum": "0.13",
        |                "maximum": "8000.00"
        |            },
        |            "image": {
        |                "normal": "https://www.mollie.com/images/payscreen/methods/paypal.png",
        |                "bigger": "https://www.mollie.com/images/payscreen/methods/paypal%402x.png"
        |            }
        |        }
        |    ]
        |}
      """.stripMargin

    val request = ListMethodsRequest()
    val maybeResponse = request.responseReads.reads(Json.parse(responseJson))

    assert(maybeResponse.asOpt.isDefined)

    maybeResponse.map { response =>
      assert(response.totalCount == 2)
      assert(response.offset == 0)
      assert(response.count == 2)

      assert(response.data.size === 2)

      assert(response.data(0).id == "ideal")
      assert(response.data(0).description == "iDEAL")

      assert(response.data(1).id == "paypal")
      assert(response.data(1).description == "PayPal")
    }
  }
}
