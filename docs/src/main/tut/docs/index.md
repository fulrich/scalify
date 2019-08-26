---
layout: docs
title: Getting Started
---

## {{page.title}}
This guide will walk you through the quickest way to get started with Scalify and Shopify.
By far the easiest way we currently offer to get started is using the Play! Framework and the [Scalify Play! Example](https://github.com/fulrich/scalify-play-example).

If you wish you can pull the Scalify libraries directly to get started but it is recommended to use the example for your first time.

### Prerequisites
#### Environment
There are a few things you will need installed before you can get started.
1. A stable release of the JDK. You can find the latest LTS release [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html).
2. The latest version of SBT. Instructions can be found [here](https://www.scala-sbt.org/download.html).

#### Shopify
There are a few things you will need to setup with Shopify before you can get started.
1. A Shopify Partners account. You can sign up for one at the [Shopify Partners](https://www.shopify.com/partners) site.
2. A Shopify App. Once you have an account you can create an app on the Shopify Partners site.
    * Once logged in to Shopify Partners choose "Apps" on the sidebar.
    * Choose "Create App" at the top right.
      * Give your app a name. 
      * The App URL is where you are planning to deploy your production app.
      * The whitelisted URLs are valid callbacks for Shopify to use. For now this can be the same as your App URL.
3. A Shopify Development store. This will be a live test store you can use while developing your app.
    * Once logged in to Shopify Partners choose "Stores" on the sidebar.
    * Choose "Add Store" at the top right.
    
### Shopify App CLI
The [Shopify App CLI](https://github.com/Shopify/shopify-app-cli) helps to streamline many of the steps required to start creating a Shopify App.
We highly recommend you install the Shopify App CLI and use it for managing your app setup and development.

It is incredibly easy to install and the full instructions can be found [here](https://github.com/Shopify/shopify-app-cli#install).

### Shopify App CLI with Scala
Currently the Shopify App CLI does not support Scala or Scalify.
Luckily it is extremely easy to work with and modify the Shopify App CLI.
We have created a [fork](https://github.com/fulrich/shopify-app-cli) of the CLI that introduces Scala as an option.

To use it first clone the [fork](https://github.com/fulrich/shopify-app-cli):

`git clone https://github.com/fulrich/shopify-app-cli.git`

Next navigate into the git directory you just cloned.
Checkout the branch that introduces Scala to the Shopify App CLI:

`git checkout hackdays-2019`

Finally the Shopify App CLI has a great way to start using a development version of the CLI. 
You can tell the Shopify App CLI to use the Scala Fork:

`shopify load-dev '/path/to/scala/fork`

### Create Your App
Now that we have the App CLI setup we can start working with a new app.
Navigation to where you would like your application code to live and run the following command:

`shopify create project project-name`

This will use the Shopify App CLI to start creating a new Shopify App.
If you loaded the forked Scala version correctly you should see the following options:
```
> 1. node embedded app
  2. rails embedded app
  3. Scala Play! Framework app (alpha)
```

Select 3 to create a skeleton Scala Play! Framework Shopify app.
This will check your dependencies to ensure you have SBT and Javac [installed](#environment).

### Running Your App
If the creation step ran successfully you should be all set to run your Scala Play! Framework Shopify app.
Run the command:

`shopify serve`

The first time you run this command the Shopify App CLI will need to generate your `.env` file it uses to define your app settings.
In your browser navigate to the App you created in the [Prerequisites](#prerequisites) step and go to your "App Setup".
At the very bottom of your App Setup page you will find your API Key and API Secret Key.
Your development store is the URL of the store you created in the [Prerequisites](#prerequisites) step, omitting the HTTP protocol. (eg. devstore.myshopify.com).

After you have entered the details the serve command will create a [ngrok](https://ngrok.com/) tunnel and spin up the Play! Framework server.
You should see something similar in your console:
```
✓ ngrok tunnel running at  https://########.ngrok.io
✓ writing .env file...
✓ ngrok tunnel running at  https://########.ngrok.io
```
This will be important as you will have to go back to your App Setup and whitelist your ngrok subdomain.
On your App Setup Page update your App URL to:

`https://########.ngrok.io/`

Then under the URLs section add the following Whitelisted Redirection URLs:
```
https://########.ngrok.io/
https://########.ngrok.io/install
https://########.ngrok.io/install-callback
```

From your console where you server is running press `ctrl + t` and it will open your browser and take you to your app installation page on your development store.

Install your App and you are setup!

Happy developing!
