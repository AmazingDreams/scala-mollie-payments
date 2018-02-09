package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Customer
import com.github.amazingdreams.mollie.testutils.MollieIntegrationSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class GetMandatesRequestTest extends MollieIntegrationSpec {

  behavior of "GetMandatesRequest"

  it should "get customer mandates" in {
    val createResult = Await.result(mollieClient.request(CreateCustomerRequest(
      name = Some("T. TEST"),
      email = Some("test@test.nl")
    )), Duration.Inf)

    assert(createResult.isRight)

    val customer = createResult.right.get

    val result = Await.result(mollieClient.request(ListMandatesRequest(
      customerId = customer.id
    )), Duration.Inf)

    assert(result.isRight)

    result.right.map { listMandatesResult =>
      assert(listMandatesResult.count == 0)
    }
  }
}
