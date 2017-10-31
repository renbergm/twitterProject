package org.twitter4j

import twitter4j.TwitterFactory

import spray.routing._
import spray.http._
import spray.json._

import akka.actor.Actor

import scala.util.{ Success, Failure }
import scala.concurrent.Future._
import scala.concurrent.ExecutionContext.Implicits.global

class MyServiceActor extends Actor with MyService {
  def actorRefFactory = context
  def receive = runRoute(rootRoute)
}

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val statusInfoFormat = jsonFormat1(StatusInfo.apply)
  implicit val emojiInfoFormat = jsonFormat1(EmojiInfo.apply)
  implicit val timeInfoFormat = jsonFormat1(TimeInfo.apply)
}

trait MyService extends HttpService {

  import MyJsonProtocol._
  import spray.httpx._
  import spray.httpx.marshalling._
  import spray.httpx.SprayJsonSupport._

  val myTotalTweets =
    path("totalTweets") {
      onComplete(PhotoObject.totalTweetsCounted) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myPhotoPercent =
    path("photoPercent") {
      onComplete(PhotoObject.getPercentage(PhotoObject.photoUrlsCount, PhotoObject.totalTweets)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myTweetsPerHrMinSec =
    path("tweetsPerHrMinSec") {
      onComplete(TimeObject.getTweetsPerTime) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myHashtagsList =
    path("hashtagsList") {
      onComplete(HashtagObject.getHashtagStats(HashtagObject.hashtagHashMap)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myUrlPercent =
    path("urlPercent") {
      onComplete(UrlObject.getPercentage(UrlObject.urlCount, PhotoObject.totalTweets)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myUrlDomainList =
    path("urlDomainList") {
      onComplete(UrlObject.getUrlStats(UrlObject.urlHashMap)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myEmojiList =
    path("emojiList") {
      onComplete(EmojiFunctions.getEmojiStats(EmojiFunctions.emojiHashMap)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myEmojiPercent =
    path("emojiPercent") {
      onComplete(EmojiFunctions.getPercentage(EmojiFunctions.emojiCount, PhotoObject.totalTweets)) {
        case Success(value) => complete(value)
        case Failure(_)    => complete("failure")
      }
    }

  val myPing =
    path("ping") {
      get{
        complete("PONG")
      }
    }

  def rootRoute = myTotalTweets ~ myPhotoPercent ~ myTweetsPerHrMinSec ~ myHashtagsList ~ myUrlPercent ~ myUrlDomainList ~ myEmojiList ~ myEmojiPercent ~ myPing

}
