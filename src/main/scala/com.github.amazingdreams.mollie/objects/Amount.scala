package com.github.amazingdreams.mollie.objects

import play.api.libs.json.Json

object Amount {
  lazy implicit val amountReads = Json.reads[Amount]
}

case class Amount(currency: String, value: String)
