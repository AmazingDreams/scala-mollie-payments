package com.github.amazingdreams.mollie.requests

import org.scalatest.FunSuite
import play.api.libs.json.Json

class CreateCustomerRequestTest extends FunSuite {

  test("has correct path") {
    val request = new CreateCustomerRequest()

    assert(request.path == "customers")
  }

  test("has correct params") {
    val request = new CreateCustomerRequest(
      name = Some("T. Test"),
      email = Some("test@test.nl")
    )

    assert((request.postData \ "name").as[String] == "T. Test")
    assert((request.postData \ "email").as[String] == "test@test.nl")
  }

  test("can decode sample response") {
    val responseJson =
      """
        |{
        |    "resource": "customer",
        |    "id": "cst_8wmqcHMN4U",
        |    "mode": "test",
        |    "name": "Customer A",
        |    "email": "customer@example.org",
        |    "locale": "nl_NL",
        |    "metadata": null,
        |    "recentlyUsedMethods": [],
        |    "createdDatetime": "2016-04-06T13:10:19.0Z"
        |}
      """.stripMargin

    val request = new CreateCustomerRequest()
    val customer = request.responseReads.reads(Json.parse(responseJson)).get

    assert(customer.name.get == "Customer A")
    assert(customer.email.get == "customer@example.org")
  }
}
