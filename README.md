# scala-mollie-payments
Create mollie payments in scala

Make sure to check out:
- https://www.mollie.com/en/docs/

## Add to your project
Currently `scala-mollie-payments` is not available on any repository. You can add this git repository manually:

```scala
lazy val scalaMolliePayments = ProjectRef(uri("git:https://github.com/AmazingDreams/scala-mollie-payments.git#master"), "scala-mollie-payments")

// Ensure you add it as a dependency
lazy val root = (project in file("."))
  .dependsOn(scalaMolliePayments)
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
