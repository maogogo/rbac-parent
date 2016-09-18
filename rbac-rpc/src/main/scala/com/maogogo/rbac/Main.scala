package com.maogogo.rbac

import com.twitter.inject.server.TwitterServer
import com.typesafe.config.Config
import com.maogogo.rbac.modules.ServicesModule
import java.net.InetSocketAddress
import com.twitter.logging.Level
import com.twitter.logging.Logging.LevelFlaggable
import com.twitter.util.Await

object Main extends TwitterServer {

  val config: Config = ServicesModule.provideConfig()
  override val adminPort = flag("admin.port", new InetSocketAddress(config.getInt("admin.port")), "")

  val level: Level = Level.ERROR
  override val levelFlag = flag("log.level", level, "")

  override def modules = Seq(ServicesModule)

  override def postWarmup() {
    val services = ServicesModule.services(injector)
    Await.all(services: _*)
    info(s"${logo}\t${adminPort}\t${config.origin()}")
    Await.ready(adminHttpServer)
  }

  private val logo = """
   ____  ____   ____
  |  _ \|  _ \ / ___|
  | |_) | |_) | |
  |  _ <|  __/| |___
  |_| \_\_|    \____|"""
}
