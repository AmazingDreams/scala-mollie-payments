package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.Method
import play.api.libs.json.{Json, Reads}

case class ListMethodsRequest(offset: Int = 0,
                              count: Int = 250,
                              recurringType: Option[String] = None,
                              locale: Option[String] = None)
  extends MollieRequest[ListMethodsResponse] {
  override def path: String = "methods"
  override def params: Seq[(String, String)] = Seq(
    ("offset", offset.toString),
    ("count", count.toString),
    ("recurringType", recurringType.getOrElse("").toString),
    ("locale", recurringType.getOrElse("").toString)
  )
  override def responseReads: Reads[ListMethodsResponse] = {
    implicit val methodReads = Method.methodReads
    Json.reads[ListMethodsResponse]
  }
}


case class ListMethodsResponse(totalCount: Int,
                               offset: Int,
                               count: Int,
                               data: Seq[Method])
  extends MollieResponse
