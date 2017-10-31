package org.twitter4j

import twitter4j._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.HashMap

case class StatusInfo(hashtagInfo: String)
case class IndexOfStatus(indexes: Array[Int])

object HashtagObject extends TweetDataRepo[String, Int] {

  def getHashtagStats(hashTagHashMap: HashMap[String, Int]): Future[Iterable[StatusInfo]] = Future[Iterable[StatusInfo]] {
    val sortedHashMap = hashTagHashMap.toSeq.sortWith(_._2 > _._2)
    sortedHashMap.map{ case (key, value) => StatusInfo("The hashtag " + key + " occured: " + value.toString + " times.")}
  }

  def updateHashtags(hashtagEntities: Array[HashtagEntity]): Future[IndexOfStatus] = Future[IndexOfStatus] {
    val hashtagsWithIndexes: Array[(twitter4j.HashtagEntity, Int)] = hashtagEntities.zipWithIndex
    val updateHashtagEntities: Array[Int] = hashtagsWithIndexes.flatMap { case (hashtag, index) => updateStatusHashtag(hashtagEntities(index.toInt).getText()) }
    IndexOfStatus(updateHashtagEntities)
  }

} 
