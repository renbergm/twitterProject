package org.twitter4j

import java.net.URI
import twitter4j._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable.HashMap

case class IndexOfUrls(indexes: Array[Int])

object UrlObject extends TweetDataRepo[String, Int] with TweetCountRepo[Int] {

  def updateUrls(urlEntities: Array[URLEntity]): Future[IndexOfUrls] = Future[IndexOfUrls] {
    val urlsWithIndexes = urlEntities.zipWithIndex
    val addingUrls: Array[Int] = urlsWithIndexes.flatMap { case (url, index) => updateStatusUrls(getUrlDomain(urlEntities(index.toInt).getExpandedURL().toString)) }
    IndexOfUrls(addingUrls)
  }

  def getUrlStats(urlMap: HashMap[String, Int]): Future[Iterable[StatusInfo]] = Future[Iterable[StatusInfo]] {
    val sortedUrlMap: Seq[(String, Int)] = urlMap.toSeq.sortWith(_._2 > _._2)
    sortedUrlMap.map{ case (key, value) => StatusInfo("The url " + key + " occured: " + value.toString + " times.")}
  }

  def getUrlDomain(url: String): String = {
    val aJavaURI = new URI(url)
    if(aJavaURI.getHost().nonEmpty){
      updateUrlCount
    }
    aJavaURI.getHost()
  }

}
