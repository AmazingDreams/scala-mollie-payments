package com.github.amazingdreams.mollie.objects

case class Issuer(id: String,
                  name: String,
                  method: String,
                  image: IssuerImages)

case class IssuerImages(normal: String,
                        bigger: String)
