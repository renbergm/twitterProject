package org.twitter4j

import twitter4j.Status
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import org.json4s._
import org.json4s.native.JsonMethods._
import scala.collection.mutable.HashMap

case class EmojiInfo(emojiDescription: String)
case class EmojiData(unified: String, unicode: String, emojiLength: Int, emojiCounter: Int, tweetText: String)
case class Field(name: Option[String], unified: Option[String], variations: Option[String], has_img_twitter: Option[String])

object EmojiFunctions extends TweetDataRepo[String, Int] with TweetCountRepo[Int] {

  implicit val formats: DefaultFormats.type = DefaultFormats

  lazy val jsonEmoji: String = scala.io.Source.fromFile("/Users/renberma/Random/TwitterProjectRepo/twitterProject/src/main/resources/emoji.json").mkString

  lazy val emojiDataList: Array[EmojiData] = (parse(jsonEmoji).extract[Array[Field]]) map { x => EmojiData(
     x.unified.getOrElse(""),
     parseToUnicode(x.unified.getOrElse("")),
     ((x.unified.getOrElse("").count(_ == '-')) + 1),
     0,
     "")}

  def parseToUnicode(hexCode: String): String = { new String(hexCode.split("-").flatMap{ codepoint => Character.toChars(Integer.parseInt(codepoint, 16)) }) }

  def getEmojiStats(emojiMap: HashMap[String, Int]): Future[Iterable[EmojiInfo]] = Future[Iterable[EmojiInfo]] {
    val sortedHashMap: Seq[(String, Int)] = emojiMap.toSeq.sortWith(_._2 > _._2)
    sortedHashMap.map{ case (key, value) => EmojiInfo("The emoji " + key + " occured: " + value.toString)}
  }

  def updateStatusEmoji(status: Status): Future[Unit] = Future[Unit] {
    var hadEmoji: Boolean = false
    emojiDataList map { emojiData =>
      val emojiCounted: Int = emojiCounter(emojiData, status.getText.toString, 0)
      if(emojiCounted!=0){
        hadEmoji = true
        if(emojiHashMap.isDefinedAt(emojiData.unicode)) {
          addToEmoji(emojiData.unicode, emojiHashMap.getOrElse(emojiData.unicode, 0), emojiCounted)
        } else {
          updateEmoji(emojiData.unicode, emojiCounted)
        }
      }
    }
    if (hadEmoji==true){
      updateEmojiCount
    }
  }

  def emojiCounter(dataEmoji: EmojiData, tweetText: String, counter: Int): Int = {
    val index: Int = tweetText.indexOfSlice(dataEmoji.unicode)
    lazy val tweetLength: Int = tweetText.length
      if((dataEmoji.emojiLength > tweetText.length) || (index == -1)){
        counter
      } else {
         val slice: String = tweetText.slice(index, dataEmoji.emojiLength)
         emojiCounter(dataEmoji, tweetText.substring(index+dataEmoji.emojiLength, tweetLength), counter + 1)
      }
  }

}
