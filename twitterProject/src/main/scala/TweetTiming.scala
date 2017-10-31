package org.twitter4j

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class TimeInfo(tweetsPerTime: String)

object TimeObject {

  def tweetsPerSecond: Long = {

    val totalTime: Long = System.currentTimeMillis - TwitterBoot.startTime
    val totalSeconds: Long = totalTime / 1000

     (PhotoObject.totalTweets, totalSeconds) match {
       case (totCount, totSecs) if totSecs > 0 => totCount / totSecs
       case _ => 0l
     }
   }

  def tweetsPerMinute: Long = tweetsPerSecond * 60
  def tweetsPerHour: Long = tweetsPerMinute * 60

  def getTweetsPerTime: Future[TimeInfo] = Future[TimeInfo] {
    TimeInfo("Tweets Per Hour/Minute/Second " + tweetsPerHour + ":" + tweetsPerMinute + ":" + tweetsPerSecond)
  }

}
