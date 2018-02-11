package com.github.amazingdreams.mollie

import com.github.amazingdreams.mollie.requests.{MollieRequest, MollieResponse, RequestMethod}
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future, blocking}
import scalaj.http.{BaseHttp, HttpConstants, HttpRequest, HttpResponse}

object MollieClient {
  final val CLIENT_VERSION = "0.1.0-SNAPSHOT"
  final val BASE_URL = "https://api.mollie.nl"
  final val API_VERSION = "v1"
  final val API_KEY_HEADER = "Authorization"

  def buildPath(path: String) = {
    val cleanedPath = {
      if (path.startsWith("/")) {
        path.tail
      }

      path
    }

    s"$BASE_URL/$API_VERSION/$cleanedPath"
  }
}

object MollieHttp extends BaseHttp(
  proxyConfig = None,
  options = HttpConstants.defaultOptions,
  charset = HttpConstants.utf8,
  sendBufferSize = 4096,
  userAgent = s"scala-mollie-payments/${MollieClient.CLIENT_VERSION}",
  compress = false
)

class MollieClient(apiKey: String, testMode: Boolean = true) {
  import MollieClient._

  def request[T](mollieRequest: MollieRequest[T])
                (implicit ec: ExecutionContext): Future[Either[Seq[String], T]] = {
    val httpRequest = createHttpRequest(mollieRequest)

    Future {
      blocking {
        handleHttpResponse[T](
          mollieRequest = mollieRequest,
          response = httpRequest.asBytes
        )
      }
    }
  }

  private def createHttpRequest[T](mollieRequest: MollieRequest[T]): HttpRequest = {
    val localRequest = MollieHttp(buildPath(mollieRequest.path))
      .method(mollieRequest.method.toString)
      .header(API_KEY_HEADER, s"Bearer $apiKey")
      .params(mollieRequest.params)

    if (mollieRequest.method == RequestMethod.POST) {
      localRequest
        .header("content-type", "application/json")
        .postData(Json.stringify(mollieRequest.postData))
    } else {
      localRequest.param("testmode", testMode.toString)
    }
  }

  private def handleHttpResponse[T](mollieRequest: MollieRequest[T],
                                    response: HttpResponse[Array[Byte]]): Either[Seq[String], T] =
    if (response.code == mollieRequest.successResponseCode) {
      val json = Json.parse(response.body)
      val parsed = mollieRequest.responseReads.reads(json)

      parsed.fold(
        invalid => Left(
          invalid.map { case (jsPath, errors) =>
            s"${jsPath.toString()}:\n" +
              s" - ${errors.map(_.message).mkString("\n")}\n"
          }
        ),
        Right(_)
      )
    } else {
      Left(Seq(
        s"ERROR ${response.code}: \n${Json.prettyPrint(Json.parse(response.body))}"
      ))
    }
}
