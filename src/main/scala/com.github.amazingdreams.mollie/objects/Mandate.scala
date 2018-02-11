package com.github.amazingdreams.mollie.objects

import play.api.libs.json.Json

object Mandate {
  lazy implicit val mandateReads = Json.reads[Mandate]
}

case class Mandate(id: String,
                   status: String,
                   method: String,
                   customerId: String,
                   details: Map[String, String],
                   createdDatetime: String)
