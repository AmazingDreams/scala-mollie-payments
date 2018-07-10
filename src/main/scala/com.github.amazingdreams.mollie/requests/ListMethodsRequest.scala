package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.objects.SequenceType.SequenceType
import com.github.amazingdreams.mollie.objects.{Amount, Method}
import play.api.libs.json.{JsPath, Reads}
import play.api.libs.functional.syntax._

case class ListMethodsRequest(sequenceType: Option[SequenceType] = None,
                              locale: Option[String] = None,
                              amount: Option[Amount] = None)
  extends MollieRequest[ListMethodsResponse] {
  implicit val methodReads = Method.methodReads

  override def path: String = "methods"
  override def params: Seq[(String, String)] = Seq(
    sequenceType.map { s =>
      ("sequenceType", s.toString)
    },
    locale.map { l =>
      ("locale", l)
    },
    amount.map { a =>
      ("amount[value]", a.value)
    },
    amount.map { a =>
      ("amount[currency]", a.currency)
    }
  ).flatten
  override def responseReads: Reads[ListMethodsResponse] = (
    (JsPath \ "count").read[Int] and
    (JsPath \ "_embedded" \ "methods").read[Seq[Method]]
  )(ListMethodsResponse.apply _)
}


case class ListMethodsResponse(count: Int,
                               methods: Seq[Method])
  extends MollieResponse
