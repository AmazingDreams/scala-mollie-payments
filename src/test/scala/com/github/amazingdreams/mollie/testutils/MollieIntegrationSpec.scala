package com.github.amazingdreams.mollie.testutils

import com.github.amazingdreams.mollie.MollieClient
import org.scalatest.FlatSpec

abstract class MollieIntegrationSpec extends FlatSpec {

  val apiKey = System.getenv("API_KEY")

  if (apiKey == null) {
    cancel("Set API_KEY=YOUR_API_KEY to run integration tests")
  }

  val mollieClient = new MollieClient(
    apiKey = apiKey,
    testMode = true
  )
}
