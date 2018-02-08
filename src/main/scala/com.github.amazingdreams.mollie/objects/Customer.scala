package com.github.amazingdreams.mollie.objects

import com.github.amazingdreams.mollie.requests.MollieResponse

case class Customer(id: String,
                    name: Option[String],
                    email: Option[String],
                    locale: Option[String],
                    metadata: Option[Map[String, String]])
  extends MollieResponse

