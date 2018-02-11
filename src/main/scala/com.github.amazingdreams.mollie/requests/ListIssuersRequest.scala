package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.{Issuer, IssuerImages}
import play.api.libs.json.{Json, Reads}

case class ListIssuersRequest(offset: Int = 0,
                              count: Int = 250)
  extends MollieRequest[ListIssuersResponse] {
  override def path: String = "issuers"
  override def params: Seq[(String, String)] = Seq(
    ("offset", offset.toString),
    ("count", count.toString)
  )
  override def responseReads: Reads[ListIssuersResponse] = {
    implicit val issuerImageReads = Json.reads[IssuerImages]
    implicit val issuerReads = Json.reads[Issuer]
    Json.reads[ListIssuersResponse]
  }
}

case class ListIssuersResponse(totalCount: Int,
                               offset: Int,
                               count: Int,
                               data: Seq[Issuer])
  extends MollieResponse
