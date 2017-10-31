package org.twitter4j

import scala.concurrent.duration._
import spray.can.Http
import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import scala.io._

object TwitterBoot extends TwitterInstance with App {

  val streamBuild = new StreamBuild()
  val startTime: Long = System.currentTimeMillis

  implicit val system = ActorSystem("MyService")
  val service = system.actorOf(Props[MyServiceActor], "demo-service")
  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)

  startTime
  streamBuild.twitterSample

}
