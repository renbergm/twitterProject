package org.twitter4j

import twitter4j.Status
import collection.mutable.HashMap
import collection.mutable.ArrayBuffer
import org.scalatest.concurrent.ScalaFutures
import concurrent.ExecutionContext.Implicits._
import scala.util.{ Success, Failure}
import twitter4j.json.DataObjectFactory
import org.scalatest._
import matchers.ShouldMatchers._

class HashtagTest extends FlatSpec with Matchers with ScalaFutures {

  val demoHashMap: HashMap[String,Int] = HashMap[String,Int]("mack" -> 3, "iHeratAwards" -> 2, "kelly" -> 6, "ryan" -> 1)
  val sortedDemoHashMap: ArrayBuffer[StatusInfo] = ArrayBuffer(StatusInfo("The hashtag kelly occured: 6 times."),
                            StatusInfo("The hashtag mack occured: 3 times."),
                            StatusInfo("The hashtag iHeratAwards occured: 2 times."),
                            StatusInfo("The hashtag ryan occured: 1 times."))
  val jsonStatus: String = """{"in_reply_to_status_id_str":null,"in_reply_to_status_id":null,"created_at":"Tue Mar 29 03:38:06 +0000 2016","in_reply_to_user_id_str":null,"source":"<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone<\/a>","retweet_count":0,"retweeted":false,"geo":null,"filter_level":"low","in_reply_to_screen_name":null,"is_quote_status":false,"id_str":"714657862031552512","in_reply_to_user_id":null,"favorite_count":0,"id":714657862031552512,"text":"I'm such a nice cousin 💁🏽🙈 #surprise","place":null,"lang":"en","favorited":false,"coordinates":null,"truncated":false,"timestamp_ms":"1459222686666","entities":{"urls":[],"hashtags":[{"indices":[27,36],"text":"surprise"}],"user_mentions":[],"symbols":[]},"contributors":null,"user":{"utc_offset":null,"friends_count":338,"profile_image_url_https":"https://pbs.twimg.com/profile_images/702659798702039040/t3UzNG9y_normal.jpg","listed_count":3,"profile_background_image_url":"http://pbs.twimg.com/profile_background_images/460920325757095937/IiaLiTSt.jpeg","default_profile_image":false,"favourites_count":1573,"description":"Canal : https://www.youtube.com/channel/UCcD_TcWkXku7ybXn_lqFHew?view_as=subscriber","created_at":"Sat Feb 08 00:14:03 +0000 2014","is_translator":false,"profile_background_image_url_https":"https://pbs.twimg.com/profile_background_images/460920325757095937/IiaLiTSt.jpeg","protected":false,"screen_name":"mgmvine","id_str":"2331075634","profile_link_color":"2FC2EF","id":2331075634,"geo_enabled":true,"profile_background_color":"1A1B1F","lang":"en","profile_sidebar_border_color":"FFFFFF","profile_text_color":"333333","verified":false,"profile_image_url":"http://pbs.twimg.com/profile_images/702659798702039040/t3UzNG9y_normal.jpg","time_zone":null,"url":"http://Instagram.com/mari_faith_cloud","contributors_enabled":false,"profile_background_tile":true,"profile_banner_url":"https://pbs.twimg.com/profile_banners/2331075634/1458592334","statuses_count":843,"follow_request_sent":null,"followers_count":147,"profile_use_background_image":true,"default_profile":false,"following":null,"name":"Mari Fe G.","location":"Costa Rica ","profile_sidebar_fill_color":"DDEEF6","notifications":null}}"""
  val testStatus: twitter4j.Status = DataObjectFactory.createStatus(jsonStatus)

  it should "My function getHashtagStats should show a success case" in {

    HashtagObject.getHashtagStats(demoHashMap) onComplete {
      case Success(value) => value should equal(sortedDemoHashMap)
      case Failure(exp) => fail(exp)
    }
  }

  it should "My function updateHashtags should show a success case" in {

    HashtagObject.updateHashtags(testStatus.getHashtagEntities()) onComplete {
      case Success(value) => HashtagObject.hashtagHashMap should equal(Map("surprise" -> 1))
      case Failure(exp) => fail(exp)
    }
  }

}
