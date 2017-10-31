package org.twitter4j

import twitter4j.TwitterFactory
import twitter4j.TwitterStreamFactory
//import org.jasypt._
//import org.jasypt.util.text.BasicTextEncryptor

trait TwitterInstance {
  val twitter = new TwitterFactory().getInstance
}

class StreamBuild {
  def twitterSample = {
    val twitterUtilities = new TwitterUtil()
    val twitterAuth = new TwitterAuth()
    val twitterStream = new TwitterStreamFactory(twitterAuth.twitterAuth()).getInstance
    val twitter4jListener = twitterUtilities.simpleStatusListener
    twitterStream.addListener(twitter4jListener)
    twitterStream.sample
  }
}

class TwitterAuth {
  def twitterAuth() = {
    val config = new twitter4j.conf.ConfigurationBuilder()
    config.setOAuthConsumerKey("")
    config.setOAuthConsumerSecret("")
    config.setOAuthAccessToken("")
    config.setOAuthAccessTokenSecret("")
    config.build
  }
}
