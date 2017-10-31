package org.twitter4j

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{ TwitterFactory, StatusListener, Status, StatusDeletionNotice, StallWarning}

class TwitterUtil {

  def simpleStatusListener = new StatusListener() {

	  def onStatus(status: Status) {

      if(status.getHashtagEntities().nonEmpty){
        HashtagObject.updateHashtags(status.getHashtagEntities())
      }

      PhotoObject.updatePhotoCount(status.getMediaEntities())

      EmojiFunctions.updateStatusEmoji(status)

      if(status.getURLEntities().nonEmpty){
        UrlObject.updateUrls(status.getURLEntities())
      }

    }

	  def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

	  def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
	  	println("Limited statuses total: " + numberOfLimitedStatuses)
	  }

	  def onException(ex: Exception) {
	  	ex.printStackTrace
	  }

	  def onScrubGeo(arg0: Long, arg1: Long) {}

	  def onStallWarning(warning: StallWarning) {
	  	println("Potential error warning: " + warning)
	  }

  }
}
