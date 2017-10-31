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

class UrlTest extends FlatSpec with Matchers with ScalaFutures {

  val urlTest: String = """https://github.com/iamcal/emoji-data/blob/master/emoji.json"""
  val urlTestDomain: String = """github.com"""
  val jsonStatus: String = """{"extended_entities":{"media":[{"display_url":"pic.twitter.com/0OcWaeGyMr","indices":[94,117],"sizes":{"small":{"w":340,"h":96,"resize":"fit"},"large":{"w":480,"h":136,"resize":"fit"},"thumb":{"w":136,"h":136,"resize":"crop"},"medium":{"w":480,"h":136,"resize":"fit"}},"id_str":"715367718514352129","expanded_url":"http://twitter.com/rNkdUwd9WhOtdoM/status/715367718619258880/photo/1","media_url_https":"https://pbs.twimg.com/media/Ce1_JlnUYAEI_8m.jpg","id":715367718514352129,"type":"photo","media_url":"http://pbs.twimg.com/media/Ce1_JlnUYAEI_8m.jpg","url":"https://t.co/0OcWaeGyMr"}]},"in_reply_to_status_id_str":null,"in_reply_to_status_id":null,"created_at":"Thu Mar 31 02:38:49 +0000 2016","in_reply_to_user_id_str":null,"source":"<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone<\/a>","retweet_count":0,"retweeted":false,"geo":null,"filter_level":"low","in_reply_to_screen_name":null,"is_quote_status":false,"id_str":"715367718619258880","in_reply_to_user_id":null,"favorite_count":0,"id":715367718619258880,"text":"レア仲間探し知属性確率ＵＰ！\n★3/22(12:00)～4/5(11:59)期間限定★\n攻撃・必殺技の優秀な知属性を迎えよう！\nhttps://t.co/pt67cZcbUH　#トレクル https://t.co/0OcWaeGyMr","place":null,"lang":"ja","favorited":false,"possibly_sensitive":false,"coordinates":null,"truncated":false,"timestamp_ms":"1459391929665","entities":{"urls":[{"display_url":"bnent.jp/optw/","indices":[64,87],"expanded_url":"http://bnent.jp/optw/","url":"https://t.co/pt67cZcbUH"}],"hashtags":[{"indices":[88,93],"text":"トレクル"}],"media":[{"display_url":"pic.twitter.com/0OcWaeGyMr","indices":[94,117],"sizes":{"small":{"w":340,"h":96,"resize":"fit"},"large":{"w":480,"h":136,"resize":"fit"},"thumb":{"w":136,"h":136,"resize":"crop"},"medium":{"w":480,"h":136,"resize":"fit"}},"id_str":"715367718514352129","expanded_url":"http://twitter.com/rNkdUwd9WhOtdoM/status/715367718619258880/photo/1","media_url_https":"https://pbs.twimg.com/media/Ce1_JlnUYAEI_8m.jpg","id":715367718514352129,"type":"photo","media_url":"http://pbs.twimg.com/media/Ce1_JlnUYAEI_8m.jpg","url":"https://t.co/0OcWaeGyMr"}],"user_mentions":[],"symbols":[]},"contributors":null,"user":{"utc_offset":null,"friends_count":1,"profile_image_url_https":"https://abs.twimg.com/sticky/default_profile_images/default_profile_3_normal.png","listed_count":0,"profile_background_image_url":"http://abs.twimg.com/images/themes/theme1/bg.png","default_profile_image":true,"favourites_count":0,"description":null,"created_at":"Tue Dec 08 08:17:26 +0000 2015","is_translator":false,"profile_background_image_url_https":"https://abs.twimg.com/images/themes/theme1/bg.png","protected":false,"screen_name":"rNkdUwd9WhOtdoM","id_str":"4494837973","profile_link_color":"0084B4","id":4494837973,"geo_enabled":false,"profile_background_color":"C0DEED","lang":"en","profile_sidebar_border_color":"C0DEED","profile_text_color":"333333","verified":false,"profile_image_url":"http://abs.twimg.com/sticky/default_profile_images/default_profile_3_normal.png","time_zone":null,"url":null,"contributors_enabled":false,"profile_background_tile":false,"statuses_count":488,"follow_request_sent":null,"followers_count":0,"profile_use_background_image":true,"default_profile":true,"following":null,"name":"トレクル","location":null,"profile_sidebar_fill_color":"DDEEF6","notifications":null}}"""
  val testStatus: twitter4j.Status = DataObjectFactory.createStatus(jsonStatus)

  UrlObject.updateStatusUrls(urlTestDomain)


  it should "My function getUrlDomain should show a url domain" in {

    UrlObject.getUrlDomain(urlTest) should be ("github.com")
  }

  it should """My function getUrlStats should show "The url ###### occured: # times."""" in {

    UrlObject.getUrlStats(UrlObject.urlHashMap) onComplete {
      case Success(value) => value should equal(ArrayBuffer(StatusInfo("The url github.com occured: 1 times.")))
      case Failure(exp) => fail(exp)
    }
  }

  it should """My function updateUrls should show the correct url HashMap."""" in {

    UrlObject.updateUrls(testStatus.getURLEntities()) onComplete {
      case Success(value) => UrlObject.urlHashMap should equal(Map("bnent.jp" -> 1, "github.com" -> 1))
      case Failure(exp) => fail(exp)
    }
  }

}
