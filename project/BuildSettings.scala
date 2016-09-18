import sbt._
import Keys._
import com.typesafe.sbt.packager.Keys._
import com.twitter.scrooge.ScroogeSBT
import com.twitter.scrooge.ScroogeSBT.autoImport._

object BuildSettings {

  lazy val basicSettings = seq(
    scalaVersion := "2.11.8",
    version := "0.0.1-SNAPSHOT",
    organization := "com.maogogo",
    resolvers ++= Dependencies.repositories
    //enablePlugins(JavaServerAppPackaging)
  )

  lazy val shellSettings = seq(
    bashScriptExtraDefines ++= IO.readLines(baseDirectory.value / "scripts" / "extra.sh")
  )

  lazy val thriftSettings = seq(
    //(scroogeThriftSourceFolder in Compile) <<= baseDirectory { _ / "../rbac-thrift" }
    (scroogeThriftSourceFolder in Compile) := baseDirectory.value
  )

}
