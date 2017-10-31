package org.twitter4j

import java.text.DecimalFormat
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait TweetCountRepo[A] {
  var totalTweets: Int = 0
  var emojiCount: Int = 0
  var photoUrlsCount: Int = 0
  var urlCount: Int = 0

  def updateEmojiCount: Unit = { emojiCount = emojiCount + 1 }
  def updateUrlCount: Unit = { urlCount = urlCount + 1 }
  def addTotalTweetsCount: Unit = { totalTweets = totalTweets + 1 }

  def addTophotoUrlCount: Unit = {
    photoUrlsCount = photoUrlsCount + 1
    totalTweets = totalTweets + 1
   }

  def getPercentage(specialTweets: Int, totalTweets: Int): Future[StatusInfo] = Future[StatusInfo] {

    val formatter = new DecimalFormat("#.#####")

    (specialTweets, totalTweets) match {
      case (countedTweets, allTweets) if allTweets > 0 =>
        val countedTweetPercentage = ((formatter.format((countedTweets).toFloat/allTweets)).toFloat * 100)
        StatusInfo("Total percentage of times occurred: " + countedTweetPercentage + "%")
      case _ => StatusInfo("Total percentage of times occurred: 0 %")
    }
  }

}
