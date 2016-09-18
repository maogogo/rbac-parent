import sbt._

object Dependencies {

  val thriftVersion = "0.9.3"
  val scroogeVersion = "4.10.0"
  val finagleVersion = "6.38.0"
  val serverVersion = "1.23.0"
  val injectVersion = "2.1.6"
  val slf4jVersion = "1.7.21"
  val logbackVersion = "1.1.7"
  val configVersion = "1.3.0"
  val guavaVersion = "19.0"
  val mysqlVersion = "6.0.4"
  val solrVersion = "0.0.13"
  val mongoVersion = "1.1.1"
  val csvVersion = "1.3.3"
  val finchVersion = "0.10.0"

  val repositories = Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases"),
    "OSS Sonatype" at "https://repo1.maven.org/maven2/",
    "twttr" at "https://maven.twttr.com/",
    "amateras-repo" at "http://amateras.sourceforge.jp/mvn/",
    "spray repo" at "http://repo.spray.io",
    "sonatype-oss-snapshot" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  val commonDependency = Seq(
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "org.scala-lang" % "scala-compiler" % "2.11.8",
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-access" % logbackVersion,
    "com.typesafe" % "config" % configVersion,
    "com.google.guava" % "guava" % guavaVersion
  )

  val serverDependency = Seq(
    "com.twitter" %% "twitter-server" % serverVersion,
    "com.twitter" %% "finagle-core" % finagleVersion,
    "com.twitter" %% "finagle-thriftmux" % finagleVersion,
    "com.twitter" %% "finagle-stats" % finagleVersion,
    "com.twitter" %% "finagle-serversets" % finagleVersion excludeAll (
      ExclusionRule(organization = "org.slf4j", name = "slf4j-jdk14"),
      ExclusionRule(organization = "org.slf4j", name = "slf4j-log4j12")),
    "com.twitter.inject" %% "inject-core" % injectVersion,
    "com.twitter.inject" %% "inject-server" % injectVersion
  )

  val finchDependency = Seq(
    "com.github.finagle" %% "finch-core" % finchVersion
  )

  val thriftDependency = Seq(
    "org.apache.thrift" % "libthrift" % thriftVersion,
    "com.twitter" %% "scrooge-core" % scroogeVersion,
    "com.twitter" %% "scrooge-runtime" % "4.5.0",
    "com.twitter" %% "scrooge-serializer" % scroogeVersion,
    "com.twitter" %% "finagle-thrift" % finagleVersion
  )

  val mysqlDependency = Seq(
    "com.twitter" %% "finagle-mysql" % finagleVersion,
    "mysql" % "mysql-connector-java" % mysqlVersion
  )

  val solrDependency = Seq(
    "com.github.takezoe" %% "solr-scala-client" % solrVersion
  )

  val redisDependency = Seq(
    "com.twitter" %% "finagle-redis" % finagleVersion
  )

  val mongoDependency = Seq(
    "org.mongodb.scala" %% "mongo-scala-driver" % mongoVersion
  )

  val csvDependency = Seq(
    "com.github.tototoshi" %% "scala-csv" % csvVersion
  )

}
