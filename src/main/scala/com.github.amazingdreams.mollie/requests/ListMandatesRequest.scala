package com.github.amazingdreams.mollie.requests
import com.github.amazingdreams.mollie.objects.Mandate
import play.api.libs.json.Json

case class ListMandatesRequest(customerId: String,
                               offset: Int = 0,
                               count: Int = 30)
  extends MollieRequest[ListMandatesResponse] {
  import Mandate.mandateReads

  override def path: String = s"customers/$customerId/mandates"
  override def responseReads = Json.reads[ListMandatesResponse]
  override def params: Seq[(String, String)] = Seq(
    ("offset", offset.toString),
    ("count", count.toString)
  )
}

case class ListMandatesResponse(totalCount: Int,
                                offset: Int,
                                count: Int,
                                data: Seq[Mandate])
  extends MollieResponse
