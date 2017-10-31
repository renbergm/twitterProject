organization  := "org.twitter4j"

version       := "0.1.0"

scalaVersion  := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.4.0"
  val sprayV = "1.3.1"
  Seq(
    "org.scala-lang" % "scala-library" % "2.11.7",
    "org.twitter4j" % "twitter4j-core" % "4.0.4",
    "org.twitter4j" % "twitter4j-stream" % "4.0.4",
    "com.typesafe.akka" % "akka-actor_2.11" % akkaV,
    "org.scalactic" %% "scalactic" % "2.2.6",
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "io.spray" %%  "spray-json" % "1.3.2",
    "org.json4s" %% "json4s-native" % "3.3.0"
  )
}
resolvers += Resolver.typesafeIvyRepo("releases")
