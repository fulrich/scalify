---
layout: home
section: "home"
title: "Home"
position: 1
technologies:
 - first: ["Scala", "Scalify libraries are purely written in Scala."]
 - second: ["ScalaTest", "ScalaTest is a fantastic testing library we use for all our testing."]
 - third: ["Play! Framework", "Scalify provides a library for specific Play! Framework wrappers."]
---

# Scalify
[![Travis (.org)](https://img.shields.io/travis/fulrich/scalify?style=flat-square)](https://travis-ci.org/fulrich/scalify)
[![Scala Versions](https://img.shields.io/badge/scala-2.12%20%7C%202.13-blue.svg?style=flat-square)](https://github.com/fulrich/scalify)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.fulrich/scalify_2.12?label=2.12&style=flat-square)](https://repo1.maven.org/maven2/com/github/fulrich/scalify_2.12/)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.fulrich/scalify_2.13?label=2.13&style=flat-square)](https://repo1.maven.org/maven2/com/github/fulrich/scalify_2.13/)

## Overview
Scalify is a collection of libraries designed to make creating a Scala App for the Shopify partners program as easy as possible.
Scalify attempts to provide wrappers and constructs to make setup as easy as possible so you can get down to work as quickly as possible.

## Quick Start
Add the core Scalify library to your build.sbt:
```scala 
libraryDependencies += "com.github.fulrich" %% "scalify" % version
```

If you are using Play! Framework you can add the Play! specific wrappers:
```scala 
libraryDependencies += "com.github.fulrich" %% "scalifyplus-play" % version
```

The Play! Framework version also comes with a set of helpers for code generation when using the Shopify App CLI.
Add the following to your `project/plugins.sbt`:
```scala 
addSbtPlugin("com.github.fulrich" %% "scalifyplus-sbt-play" % version)
```

** Note: Newest version can be found on the badges at the top of this page.

## Important Links
[Shopify](https://ww.shopify.com)

[Shopify Partners](https://www.shopify.com/partners)

[Shopify Developers](https://developers.shopify.com/)

## Contributors
Maher Gamal ([maherg](https://github.com/maherg))

Joanna Klekotka ([jklekotka](https://github.com/jklekotka))
 