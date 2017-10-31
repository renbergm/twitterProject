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

class PhotoTest extends FlatSpec with Matchers with ScalaFutures {

  val jsonPhotoStatus: String = """{"extended_entities":{"media":[{"display_url":"pic.twitter.com/a49QRszLfx","indices":[51,74],"sizes":{"small":{"w":340,"h":166,"resize":"fit"},"large":{"w":750,"h":366,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"medium":{"w":600,"h":293,"resize":"fit"}},"id_str":"715353865218994177","expanded_url":"http://twitter.com/eb0nyy/status/715353869006536704/photo/1","media_url_https":"https://pbs.twimg.com/media/Ce1yjOEUsAEJ7-T.jpg","id":715353865218994177,"type":"photo","media_url":"http://pbs.twimg.com/media/Ce1yjOEUsAEJ7-T.jpg","url":"https://t.co/a49QRszLfx"}]},"in_reply_to_status_id_str":null,"in_reply_to_status_id":null,"created_at":"Thu Mar 31 01:43:47 +0000 2016","in_reply_to_user_id_str":null,"source":"<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone<\/a>","retweet_count":0,"retweeted":false,"geo":null,"filter_level":"low","in_reply_to_screen_name":null,"is_quote_status":false,"id_str":"715353869006536704","in_reply_to_user_id":null,"favorite_count":0,"id":715353869006536704,"text":"I wish Madison wouldn't say things like this to me https://t.co/a49QRszLfx","place":null,"lang":"en","favorited":false,"possibly_sensitive":false,"coordinates":null,"truncated":false,"timestamp_ms":"1459388627660","entities":{"urls":[],"hashtags":[],"media":[{"display_url":"pic.twitter.com/a49QRszLfx","indices":[51,74],"sizes":{"small":{"w":340,"h":166,"resize":"fit"},"large":{"w":750,"h":366,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"medium":{"w":600,"h":293,"resize":"fit"}},"id_str":"715353865218994177","expanded_url":"http://twitter.com/eb0nyy/status/715353869006536704/photo/1","media_url_https":"https://pbs.twimg.com/media/Ce1yjOEUsAEJ7-T.jpg","id":715353865218994177,"type":"photo","media_url":"http://pbs.twimg.com/media/Ce1yjOEUsAEJ7-T.jpg","url":"https://t.co/a49QRszLfx"}],"user_mentions":[],"symbols":[]},"contributors":null,"user":{"utc_offset":-10800,"friends_count":645,"profile_image_url_https":"https://pbs.twimg.com/profile_images/713412634079600644/CpTT5-Qt_normal.jpg","listed_count":4,"profile_background_image_url":"http://pbs.twimg.com/profile_background_images/840193404/7e735e6eb0c1d0801847d1f85e9cbc6f.png","default_profile_image":false,"favourites_count":32990,"description":"uhm yea","created_at":"Wed Aug 08 18:27:22 +0000 2012","is_translator":false,"profile_background_image_url_https":"https://pbs.twimg.com/profile_background_images/840193404/7e735e6eb0c1d0801847d1f85e9cbc6f.png","protected":false,"screen_name":"eb0nyy","id_str":"745701434","profile_link_color":"F5ABB5","id":745701434,"geo_enabled":false,"profile_background_color":"F9F9F9","lang":"en","profile_sidebar_border_color":"FFFFFF","profile_text_color":"634047","verified":false,"profile_image_url":"http://pbs.twimg.com/profile_images/713412634079600644/CpTT5-Qt_normal.jpg","time_zone":"Atlantic Time (Canada)","url":"http://eb0nyy.vsco.co/","contributors_enabled":false,"profile_background_tile":false,"profile_banner_url":"https://pbs.twimg.com/profile_banners/745701434/1458971532","statuses_count":36586,"follow_request_sent":null,"followers_count":972,"profile_use_background_image":true,"default_profile":false,"following":null,"name":"champagnemami","location":"973","profile_sidebar_fill_color":"E3E2DE","notifications":null}}"""
  val testStatus: twitter4j.Status = DataObjectFactory.createStatus(jsonPhotoStatus)
  PhotoObject.updatePhotoCount(testStatus.getMediaEntities())

  it should "My function photoUrlsCount should match correctly on an integer" in {

    PhotoObject.photoUrlsCount should be (1)
  }

  it should "My function totalTweetsCounted should show a success case" in {

    PhotoObject.totalTweetsCounted onComplete {
      case Success(value) => value should equal(StatusInfo("1"))
      case Failure(exp) => fail(exp)
    }
  }

}
