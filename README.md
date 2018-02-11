# scala-mollie-payments
Create mollie payments in scala

Make sure to check out:
- https://www.mollie.com/en/docs/

## Add to your project
Currently `scala-mollie-payments` is available Sonatype Snapshots:

```scala
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
libraryDependencies += "com.github.amazingdreams" %% "scala-mollie-payments" % "0.1.0-SNAPSHOT"
```

## Connecting to mollie
You can find your api keys on the mollie dashboard. Also make sure you are not mixing up testMode and the api key!

```scala
import com.github.amazingdreams.mollie.MollieClient

val mollieClient = new MollieClient(
  apiKey = "YOUR_API_KEY",
  testMode = true
)
```

## Creating a bare payment
This will redirect the user to mollie's pages, where they can select the payment method and enter details.

```scala
import com.github.amazingdreams.mollie.requests.CreatePaymentRequest

mollieClient.request(CreatePaymentRequest(
  amount = 10d,
  description = "test",
  redirectUrl = "http://example.com/redirect",
  webhookUrl = "http://example.com/webhook"
)).map { createPaymentResult => {
  createPaymentResult.fold(
    errors => println(errors),
    payment => println(payment)
  )
})
```

## Creating a customer
This creates a new customer. The customer can be used to e.g. automatically create payments and have them be paid immediately. Please refer to the documentation: https://www.mollie.com/en/docs/recurring

```scala
com.github.amazingdreams.mollie.requests.CreateCustomerRequest

mollieClient.request(CreateCustomerRequest(
  name = Some("MR. Test"),
  email = Some("test@test.nl")
)).map { createCustomerResult => {
  createCustomerResult.fold(
    errors => println(errors),
    customer => println(customer)
  )
})
```
