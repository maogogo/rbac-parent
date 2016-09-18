import sbt._
import Keys._
import com.typesafe.sbt.packager.archetypes._
import com.typesafe.sbt.SbtScalariform._


object RbacProject extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt.in(ThisBuild) := (state => s"[${Project.extract(state).currentRef.project}] > ")
  }

  lazy val root = Project("rbac", file("."))
    .aggregate(thrift, rest, rpc, common)
    .settings(defaultScalariformSettings: _*)

  lazy val common = Project("rbac-common", file("rbac-common"))
    .settings(basicSettings: _*)
    .settings(libraryDependencies ++= commonDependency)
    .settings(libraryDependencies ++= serverDependency)
    .settings(libraryDependencies ++= mysqlDependency)
    .settings(libraryDependencies ++= solrDependency)
    .settings(libraryDependencies ++= redisDependency)
    .settings(libraryDependencies ++= mongoDependency)
    .settings(libraryDependencies ++= csvDependency)

  lazy val rest = Project("rbac-rest", file("rbac-rest"))
    .dependsOn(thrift).dependsOn(common)
    .settings(basicSettings: _*)
    .enablePlugins(JavaServerAppPackaging)
    .settings(libraryDependencies ++= finchDependency)

  lazy val rpc = Project("rbac-rpc", file("rbac-rpc"))
    .dependsOn(thrift).dependsOn(common)
    .settings(basicSettings: _*)
    .enablePlugins(JavaServerAppPackaging)

  lazy val thrift = Project("rbac-thrift", file("rbac-thrift"))
    .settings(basicSettings: _*)
    .settings(thriftSettings: _*)
    .settings(libraryDependencies ++= thriftDependency)

}
