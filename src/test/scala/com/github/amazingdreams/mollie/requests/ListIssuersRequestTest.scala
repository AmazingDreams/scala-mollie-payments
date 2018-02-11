package com.github.amazingdreams.mollie.requests

import org.scalatest.FunSuite
import play.api.libs.json.Json

class ListIssuersRequestTest extends FunSuite {

  test("has correct path") {
    val listIssuersRequest = new ListIssuersRequest()

    assert(listIssuersRequest.path == "issuers")
  }

  test("has correct parameters") {
    val listIssuersRequest = new ListIssuersRequest(
      count = 10,
      offset = 25
    )
    val paramsMap = listIssuersRequest.params.toMap

    assert(paramsMap("count") == "10")
    assert(paramsMap("offset") == "25")
  }

  test("can decode sample response") {
    val responseJson =
      """
        |{
        |    "totalCount": 3,
        |    "offset": 0,
        |    "count": 3,
        |    "data": [
        |        {
        |            "resource": "issuer",
        |            "id": "ideal_ABNANL2A",
        |            "name": "ABN AMRO",
        |            "method": "ideal",
        |            "image": {
        |                "normal": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/ABNANL2A.png",
        |                "bigger": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/ABNANL2A%402x.png"
        |             }
        |        }, {
        |            "resource": "issuer",
        |            "id": "ideal_ASNBNL21",
        |            "name": "ASN Bank",
        |            "method": "ideal",
        |            "image": {
        |                "normal": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/ASNBNL21.png",
        |                "bigger": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/ASNBNL21%402x.png"
        |            }
        |        }, {
        |            "resource": "issuer",
        |            "id": "ideal_INGBNL2A",
        |            "name": "ING",
        |            "method": "ideal",
        |            "image": {
        |                "normal": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/INGBNL2A.png",
        |                "bigger": "https://www.mollie.com/images/checkout/v2/ideal-issuer-icons/INGBNL2A%402x.png"
        |            }
        |        }
        |    ]
        |}
      """.stripMargin

    val listIssuersRequest = new ListIssuersRequest()
    val maybeResponse = listIssuersRequest.responseReads.reads(Json.parse(responseJson))

    assert(maybeResponse.asOpt.isDefined)

    maybeResponse.map { response =>
      assert(response.totalCount == 3)
      assert(response.offset == 0)
      assert(response.count == 3)

      assert(response.data.size === 3)

      assert(response.data(0).id == "ideal_ABNANL2A")
      assert(response.data(0).name == "ABN AMRO")
    }
  }
}
