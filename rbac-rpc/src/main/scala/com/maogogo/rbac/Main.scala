package com.maogogo.rbac

import com.twitter.util.Await
import com.typesafe.config.Config
import java.net.InetSocketAddress
import com.google.inject.Guice
import com.maogogo.rbac.modules.ServicesModule
import com.twitter.inject.server.TwitterServer
import com.twitter.logging.Level
import com.twitter.logging.Logging.LevelFlaggable

object Main extends TwitterServer {

  lazy val config = ServicesModule.provideConfig()

  val level: Level = Level.ERROR
  override val levelFlag = flag("", level, "")

  override val adminPort = flag("admin.port", new InetSocketAddress(config.getInt("admin.port")), "")
  override def modules = Seq(ServicesModule)

  lazy val sign = s"""
${"*" * 80}
    RPC: ${adminPort} started
${"*" * 80} """

  override def postWarmup() {
    val services = ServicesModule.services(injector)
    Await.all(services: _*)
    info(sign)
    Await.ready(adminHttpServer)
  }

}
