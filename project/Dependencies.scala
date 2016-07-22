import sbt._

object Dependencies {

  val thriftVersion = "0.9.3"
  val scroogeVersion = "4.8.0"
  val finagleVersion = "6.36.0"
  val finchVersion = "0.10.0"
  val logbackVersion = "1.1.7"
  val slf4jVersion =  "1.7.21"
  val guavaVersion = "19.0"
  //val slickVersion = "3.1.1"
  val configVersion = "1.3.0"
  val finatraVersion = "2.1.6"
  val injectVersion = "2.1.6"
  val serverVersion = "1.21.0"
  val guiceVersion = "4.0.1"
  val mysqlVersion = "6.0.3"
  val json4sVersion = "3.4.0"

  val repositories = Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    "OSS Sonatype" at "https://repo1.maven.org/maven2/",
    "twttr" at "https://maven.twttr.com/" ,
    "amateras-repo" at "http://amateras.sourceforge.jp/mvn/",
    "spray repo" at "http://repo.spray.io"
  )

  val commonDependency = Seq(
    "org.scalatest" %% "scalatest" % "2.2.6" % "test",
    "org.scala-lang" % "scala-compiler" % "2.11.8",
    "com.google.guava" % "guava" % guavaVersion,
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-access" % logbackVersion,
    "com.typesafe" % "config" % configVersion,
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion
  )

  val twttrDependency = Seq(
    "com.twitter" %% "twitter-server" % serverVersion,
    "com.twitter" %% "finagle-core" % finagleVersion,
    "com.twitter" %% "finagle-thrift" % finagleVersion,
    "com.twitter" %% "finagle-thriftmux" % finagleVersion,
    "com.twitter" %% "finagle-stats" % finagleVersion,
    "com.twitter" %% "finagle-serversets" % finagleVersion,
    "com.twitter.inject" %% "inject-core" % injectVersion,
    "com.twitter.inject" %% "inject-server" % injectVersion,
    "com.twitter.inject" %% "inject-thrift-client" % injectVersion
  )

  val finchDependency = Seq (
    "com.github.finagle" %% "finch-core" % finchVersion,
    "com.github.finagle" %% "finch-oauth2" % finchVersion,
    "com.github.finagle" %% "finch-json4s" % finchVersion
  )

  val mysqlDependency = Seq(
    "com.twitter" %% "finagle-mysql" % finagleVersion,
    "mysql" % "mysql-connector-java" % mysqlVersion
  )

  val thriftDependency = Seq(
    "org.apache.thrift" % "libthrift" % thriftVersion,
    "com.twitter" %% "scrooge-core" % scroogeVersion,
    "com.twitter" %% "scrooge-runtime" % "4.5.0",
    "com.twitter" %% "scrooge-serializer" % scroogeVersion
  )

}
