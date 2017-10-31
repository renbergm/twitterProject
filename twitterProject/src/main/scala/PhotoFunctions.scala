package org.twitter4j

import twitter4j._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object PhotoObject extends TweetCountRepo[Int] {

  val photoPercent: Future[StatusInfo] = getPercentage(photoUrlsCount, totalTweets)

  def totalTweetsCounted: Future[StatusInfo] = Future[StatusInfo] {
    StatusInfo(totalTweets.toString)
  }

  def updatePhotoCount(mediaImageEntity: Array[MediaEntity]): Unit = {
    if(mediaPhotoParser(mediaImageEntity).contains("photo")) {
      addTophotoUrlCount
    } else {
      addTotalTweetsCount
    }
  }

  def mediaPhotoParser(mediaImageEntity: Array[MediaEntity]): String = {
    try {
        mediaImageEntity(0).getType().mkString
    } catch {
      case ex: ArrayIndexOutOfBoundsException =>{
        "Missing-file-exception"
      }
    }
  }

}
