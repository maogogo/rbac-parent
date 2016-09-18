package com.maogogo.rbac

import com.twitter.inject.server.TwitterServer
import com.twitter.logging.Level
import com.twitter.logging.Logging.LevelFlaggable
import com.typesafe.config.Config
import com.maogogo.rbac.modules.ServicesModule
import java.net.InetSocketAddress
import com.twitter.finagle.Http
import com.twitter.util.Await

object Main extends TwitterServer {

  val config: Config = ServicesModule.provideConfig()
  override val adminPort = flag("admin.port", new InetSocketAddress(config.getInt("admin.port")), "")
  val httpPort = flag("http.port", new InetSocketAddress(config.getInt("http.port")), "")

  val level: Level = Level.ERROR
  override val levelFlag = flag("log.level", level, "")

  override def modules = Seq(ServicesModule)

  override def postWarmup() {
    val service = ServicesModule.endpoints(injector).toService

    //Http.serve(httpPort(), service)
    Http.server.serveAndAnnounce(s"zk!127.0.0.1:2181!/maogogo/rest", httpPort(), service)
    //Http.server.serveAndAnnounce(name, addr, service)
    info(s"${logo}\t${adminPort}\t${ServicesModule.zk}")
    Await.ready(adminHttpServer)
  }

  val logo = """
   ____           _
  |  _ \ ___  ___| |_
  | |_) / _ \/ __| __|
  |  _ <  __/\__ \ |_
  |_| \_\___||___/\__|"""

}
