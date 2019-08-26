---
layout: docs
title: Plus SBT Play! Framework
---

## {{page.title}}
Scalify Plus SBT Play is the smallest library of Scalify.
It's goal is to provide convenience tasks to your SBT build file when working with Scalify.
Right now this means it provides some task to help generate code for your application.

Scalify Plus SBT Play is a SBT plugin. 
You add it to your application by adding it to your `project/plugins.sbt` file.
It is an `AutoPlugin` so you do not need to enable the plugin.

### Configuration Generation
Currently there is only one Scalify Plus SBT Play task.
This task works with the Shopify App CLI to generate development and production `shopify.conf` files for your application.
This requires you to be using the Shopify App CLI because it generates the `shopify.conf` files from the CLI's `.env` file.

Run the following command to generate the shopify.conf files.
```
sbt "shopifyGenerate shopify-conf"
```

### Future
We hope to expand on the Scalify Plus SBT Play to allow generation of some basic controllers and other features.