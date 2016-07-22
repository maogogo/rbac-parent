import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform.scalariformSettings
import com.typesafe.sbt.packager.archetypes._


object RbacProject extends Build {

  import BuildSettings._
  import Dependencies._

  override lazy val settings = super.settings :+ {
    shellPrompt.in(ThisBuild) := (state => s"[${Project.extract(state).currentRef.project}] > ")
  }

  lazy val root = Project("rbac", file("."))
    .aggregate(thrift, rest, rpc)
    .settings(basicSettings: _*)
    .settings(scalariformSettings: _*)

  lazy val rest = Project("rbac-rest", file("rbac-rest"))
    .dependsOn(thrift)
    .settings(basicSettings: _*)
    .settings(scalariformSettings: _*)
    .enablePlugins(JavaServerAppPackaging)
    .settings(libraryDependencies ++= commonDependency)
    .settings(libraryDependencies ++= twttrDependency)
    .settings(libraryDependencies ++= thriftDependency)
    .settings(libraryDependencies ++= finchDependency)

  lazy val rpc = Project("rbac-rpc", file("rbac-rpc"))
    .dependsOn(thrift)
    .settings(basicSettings: _*)
    .settings(scalariformSettings: _*)
    .enablePlugins(JavaServerAppPackaging)
    .settings(libraryDependencies ++= commonDependency)
    .settings(libraryDependencies ++= twttrDependency)
    .settings(libraryDependencies ++= thriftDependency)
    .settings(libraryDependencies ++= mysqlDependency)


  lazy val thrift = Project("rbac-thrift", file("rbac-thrift"))
    .settings(basicSettings: _*)
    .settings(thriftSettings: _*)
    .settings(libraryDependencies ++= commonDependency)
    .settings(libraryDependencies ++= twttrDependency)
    .settings(libraryDependencies ++= thriftDependency)

}
