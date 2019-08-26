---
layout: docs
title: Scalify
---

## {{page.title}}
### Overview
The main Scalify library contains all the core logic required to build a Shopify App.
The core of Scalify is meant to be framework agnostics. 
The goal is for you to be able to use it with any framework you prefer.
To this end we try to avoid making a lot of decision within the library and instead try to provide constructs to make your developing easier.

### Configuration  
At the core of the Scalify library is the ShopifyConfiguration object. 
This is required for all of the communication to and from Shopify to function.
As stated in our overview we attempt to make no decision for how you will get this data into your application so it is up to you to create the ShopifyConfiguration construct when needed.

### HMAC Validation
Shopify's API will always contain an HMAC parameter that can be used to verify requests from Shopify.
You can read more about this HMAC verification [here](https://help.shopify.com/en/api/getting-started/authentication/oauth#verification).
Scalify gives you a few ways to easily handle this verification step.

#### Simple Raw Verification
This step requires the most work from the developers standpoint but offers maximum flexibility.
In this case you provide the HMAC value and the payload you wish to validate.
You are responsible for sorting the payload into the proper order per Shopify's [documentation](https://help.shopify.com/en/api/getting-started/authentication/oauth#verification).

```scala
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.ShopifyHmac

object ExampleRawVerification {
  // You can pass the configuration directly but Scalify has it as an implicit parameter in most circumstances
  implicit val configuration: ShopifyConfiguration = MyConfigurationStore.get
  
  def handleValidation(payload: String): Boolean = {
    // You must extract the HMAC Value
    val hmacValue = extractHmac(payload)
    // You must sort the payload 
    val sortedPayload = sortPayload(payload)

    // Now you can use Scalify's helper to validate your HMAC
    ShopifyHmac.validate(hmac, payload)
  }
  
  def extractHmac(payload: String) = ???
  def sortPayload(payload: String) = ???
}
```

#### Structured Raw Verification
The simple ShopifyHmac validator only returns a boolean value. 
Sometimes you may need a more useful construct for passing around HMAC validation and Scalify provides that using our Hmac monad.
An example of that usage is seen below:

```scala
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.{Hmac, Invalid, Valid}

object ExampleStructuredRawVerification {
  // You can pass the configuration directly but Scalify has it as an implicit parameter in most circumstances
  implicit val configuration: ShopifyConfiguration = MyConfigurationStore.get
  
  def handleValidation(payload: String): String = {
    // You must extract the HMAC Value
    val hmacValue = extractHmac(payload)
    // You must sort the payload 
    val sortedPayload = sortPayload(payload)

    Hmac(hmac, payload) match {
      case Valid(payload) => s"Validated HMAC for payload: $payload"
      case Invalid => "Invalid HMAC was provided."
    } 
  }
  
  def extractHmac(payload: String) = ???
  def sortPayload(payload: String) = ???
}
```

#### Using Scala URI
Scalify also offers a bit more help with your HMAC verification if you prefer.
Scalify uses the fantastic [Scala URI library](https://github.com/lemonlabsuk/scala-uri) which is a lightweight Scala library for building and parsing URIs.
If you choose this route Scalify will handle extracting the HMAC value as well as ensuring the payload is sorted:

```scala
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.ShopifyHmac

object ExampleScalaUriVerification {
  // You can pass the configuration directly but Scalify has it as an implicit parameter in most circumstances
  implicit val configuration: ShopifyConfiguration = MyConfigurationStore.get
  
  def handleValidation(payload: String): Option[Hmac[QueryString]] = {
    // There are many ways to create a Scala URI Query String, use the method that works best for your case
    val scalaUriQueryString = QueryString.parse(payload)
    
    // Now you can validate the HMAC
    ShopifyHmac.validate(scalaUriQueryString)
  }
}
```

The QueryString HMAC validation provides you with the structured HMAC monad so you can provide meaningful error responses:

```scala
import com.github.fulrich.scalify.ShopifyConfiguration
import com.github.fulrich.scalify.hmac.ShopifyHmac

object ExampleHmacResponses {  
  def hmacResponse(hmac: Option[Hmac[QueryString]]): String = hmac match {
    case Some(Valid(queryString)) => s"HMAC validation succeeded for: $queryString"
    case Some(Invalid) => s"HMAC validation failed."
    case None => s"No HMAC parameter was provided."
  }
}
```

### URI Bindings
Most Frameworks offer a way to parse URI values and bind them to concrete Scala classes.
Scalify provides you with the concrete Scala classes and you can use your framework's preferred binding method.
However it also offers it's own binding system that works with the [Scala URI library](https://github.com/lemonlabsuk/scala-uri).
Feel free to use this instead of your framework's binding:

#### Bind
```scala
import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.serialization.url._
import org.scalactic._

object ScalifyUriBindingExample {
  def installRoute(uri: String): String = {
    bind[InstallParameters](uri) match {
      case Good(parameters) => s"A parsed InstallParameters case class $parameters"
      case Bad(errors) => s"A Seq of all the parsing errors that occurred: $errors"
    } 
  }
}
```

You will notice two new constructs we haven't discussed yet: `Good` and `Bad`. 
These come from the amazing [Scalactic library](http://www.scalactic.org/).
Specifically these are part of the [Scalactic Or](http://www.scalactic.org/user_guide/OrAndEvery). 
Scalify uses this to handle error responses as it allows us to collect all errors that occurred during a process which then allows you to provide more comprehensive error responses.

In the example above we used a straight URI string and allowed the Scala URI library to parse it.
Scalify offers other ways to handle bindings and the full interface is below:
```scala
object Bindings {
  // Optionally bind on a full URI String
  def optionalBind(key: String, uri: String): Option[UrlBind[A]]

  // Optionally bind on a QueryString object from Scala URI
  def optionalBind(key: String, query: QueryString): Option[UrlBind[A]]
  
  // Optionally bind on a Scala representation of Query Parameters: Map[String, Seq[String]]
  def optionalBind(key: String, queryMap: QueryMap): Option[UrlBind[A]]


  // Bind on a full URI String
  def bind(key: String, uri: String): UrlBind[A]

  // Bind on a QueryString object from Scala URI
  def bind(key: String, query: QueryString): UrlBind[A]

  // Bind on a Scala representation of Query Parameters: Map[String, Seq[String]]
  def bind(key: String, queryMap: QueryMap): UrlBind[A]
}
```  

#### Unbind
If you need to call back to Shopify with parameters Scalify also offers an unbind method (also useful for testing).
If you have a constructed object you can call unbind to create a ScalaUri QueryString of the URI parameters
```scala
import com.github.fulrich.scalify.installation.InstallParameters
import com.github.fulrich.scalify.serialization.url._

object UnbindExample {
  val installParameters = InstallParameters(shop = "test.myshopify.com", timestamp = Instant.now)
  
  // Returns a QueryString of the InstallParameters object
  unbind(installParameters)
}
```
