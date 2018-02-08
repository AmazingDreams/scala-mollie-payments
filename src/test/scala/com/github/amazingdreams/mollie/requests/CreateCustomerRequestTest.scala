package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Customer
import com.github.amazingdreams.mollie.testutils.MollieIntegrationSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class CreateCustomerRequestTest extends MollieIntegrationSpec {

  behavior of "CreateCustomerRequest"

  it should "Create a customer" in {
    val result = Await.result(mollieClient.request(CreateCustomerRequest(
      name = Some("MR. Test"),
      email = Some("test@test.nl")
    )), Duration.Inf)

    assert(result.isRight == true)

    result.right.map {
      case c: Customer =>
        assert(c.id != "")
        assert(c.name.get == "MR. Test")
        assert(c.email.get == "test@test.nl")
    }
  }

}
