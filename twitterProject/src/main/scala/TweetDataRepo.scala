package org.twitter4j

import scala.collection.mutable.HashMap

trait TweetDataRepo[A, B] {

  val emojiHashMap = HashMap.empty[String, Int]
  val hashtagHashMap = HashMap.empty[String, Int]
  val urlHashMap = HashMap.empty[String, Int]

  def updateEmoji (key: String, value: Int): Option[Int] = { emojiHashMap.put(key, value) }
  def updateHashtag (key: String, value: Int): Option[Int] = { hashtagHashMap.put(key, value) }
  def updateUrls (key: String, value: Int): Option[Int] = { urlHashMap.put(key, value) }
  def addToEmoji (key: String, value: Int, emojiCount: Int): Option[Int] = { emojiHashMap.put(key, value.toInt + emojiCount) }
  def addToHashtag (key: String, value: Int, hashtagCount: Int): Option[Int] = { hashtagHashMap.put(key, value.toInt + hashtagCount) }
  def addToUrls (key: String, value: Int, urlCount: Int): Option[Int] = { urlHashMap.put(key, value + urlCount) }

  def updateStatusHashtag(hashtag: String): Option[Int] = {
    if(hashtagHashMap.isDefinedAt(hashtag)) {
      addToHashtag(hashtag, hashtagHashMap.getOrElse(hashtag, 0), 1)
    } else {
      updateHashtag(hashtag, 1)
    }
  }

  def updateStatusUrls(url: String): Option[Int] = {
    if(urlHashMap.isDefinedAt(url)) {
      addToUrls(url, urlHashMap.getOrElse(url, 0), 1)
    } else {
      updateUrls(url, 1)
    }
  }

}
