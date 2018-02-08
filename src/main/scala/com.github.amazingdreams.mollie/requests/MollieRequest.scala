package com.github.amazingdreams.mollie.requests

import com.github.amazingdreams.mollie.requests.RequestMethod.RequestMethod
import play.api.libs.json.{JsObject, Reads}

object RequestMethod extends Enumeration {
  type RequestMethod = Value

  val GET = Value("GET")
  val POST = Value("POST")
  val DELETE = Value("DELETE")
}

trait MollieRequest {
  def method: RequestMethod = RequestMethod.GET
  def params: Seq[(String, String)] = Seq.empty
  def path: String
  def postData: JsObject = JsObject.empty
  def responseReads: Reads[_ <: MollieResponse]
  def successResponseCode: Int = 200
}
trait MollieResponse
