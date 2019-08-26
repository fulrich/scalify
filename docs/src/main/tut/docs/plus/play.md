---
layout: docs
title: Plus Play! Framework
---

## {{page.title}}
Scalify Plus Play! is meant to wrap Scalify to better integrate with the [Play! Framework](https://www.playframework.com/).
Add the Scalify Plus Play! library to your Play! Framework `build.sbt` along with core Scalify to being using the library.

You also need to enable the Scalify Plus Play! module in your configuration.
In your `.conf` file add the following line:

```
play.modules.enabled += "com.github.fulrich.scalify.play.ScalifyModule"
```

### Configuration
In the core Scalify documentation the `ShopifyConfiguration` object is discussed.
For Scalify Plus Play! this object is retrieved from Play!'s standard [configuration file](https://www.playframework.com/documentation/2.7.x/ConfigFile).

Scalify expects the config file to contain a section in the format below:
```hocon
shopify {
  apiKey = "DEVELOPMENT_SHOPIFY_API_KEY"
  apiSecret = "DEVELOPMENT_SHOPIFY_API_SECRET"
  scopes = [write_products,write_customers,write_draft_orders]
}
```

This will be parsed and adding to the Guice injection for you by Scalify. 
You can then access the ShopifyConfiguration by injecting it where needed:

```scala
package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import com.github.fulrich.scalify.ShopifyConfiguration

@Singleton
class HomeController @Inject()(cc: ControllerComponents, configuration: ShopifyConfiguration) extends AbstractController(cc) {
  
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(s"Configuration scopes: ${configuration.scopes.mkString(",")}")
  }
}
```

### Actions
Play! Framework Controllers are made up of Actions. Actions process a Request into a Result.
Scalify provides some Actions to help you get straight to the coding your domain without needing to hassle with Shopify specific handling.

#### HMAC Action
The HMAC Action is probably the core and most useful action provided by Scalify.
The HMAC Action will process any request coming through and ensure it passed HMAC validation based upon your Shopify Configuration.
If validation passes your Action content will run if not error responses will be returned to the calling party.

An example of the HMAC action is below:
```scala
@Singleton
class HomeController @Inject()(cc: ControllerComponents, hmacAction: HmacAction) extends AbstractController(cc) {
  
  def index() = hmacAction { implicit request: HmacRequest[AnyContent] =>
    // This will only be run if the HMAC validation was successful

    // The HmacRequest includes the payload as a Scala URI Querystring
    val queryString: QueryString = request.payload
    
    // The HmacRequest also includes the original Play! Request
    val originalRequest: Request[AnyContent] = request.request
  
    Ok(s"HMAC Validated!")
  }
}
```

#### Install Actions
The OAuth installation process for Shopify Apps is usually one of the only barriers to entry to building your App.
Scalify aims to alleviate this by providing Actions that will simply the installation process.
Scalify provides two install actions. 
One for the initial install request and one for the callback request when the Merchant has authorized your application.

The install action will build on top of the HMAC Action and further parse the URI parameters into an InstallParameters object.
```scala
@Singleton
class InstallController @Inject()(cc: ControllerComponents, actions: InstallActions) extends AbstractController(cc) {
  
  def index() = actions.installAction { implicit request: InstallRequest[AnyContent] =>
    // This will only be run if the HMAC validation was successful and InstallParameters could be parsed

    // The InstallRequest includes an InstallParameters class
    val installParameters: InstallParameters = request.parameters
    
    // The InstallRequest also includes a wrapped HmacRequest
    val hmacRequest: HmacRequest[AnyContent] = request.request
  
    Ok(s"HMAC Validated and InstallParameters parsed!")
  }
}
```

The authorize action will build on top of the HMAC Action and further parse the URI parameters into an AuthorizeConfirmation object.
```scala
@Singleton
class InstallController @Inject()(cc: ControllerComponents, installActions: InstallActions) extends AbstractController(cc) {
  
  def index() = actions.authorizeAction { implicit request: AuthorizeRequest[AnyContent] =>
    // This will only be run if the HMAC validation was successful and AuthorizeConfirmation could be parsed

    // The InstallRequest includes an InstallParameters class
    val authorizeConfirmation: AuthorizeConfirmation = request.confirmation
    
    // The AuthorizeRequest also includes a wrapped HmacRequest
    val hmacRequest: HmacRequest[AnyContent] = request.request
  
    Ok(s"HMAC Validated and AuthorizeConfirmation parsed!")
  }
}
```
