package com.maogogo.rbac

import java.net.InetSocketAddress
import com.twitter.util.Await
import com.google.inject.Guice
import com.maogogo.rbac.modules.ServicesModule
import com.twitter.finagle.Http
import com.twitter.inject.server.TwitterServer
import com.twitter.logging.Level
import com.twitter.logging.Logging.LevelFlaggable

object Main extends TwitterServer {

  override val adminPort = flag("admin.port", new InetSocketAddress(20003), "")
  val httpPort = flag("http.port", new InetSocketAddress(9000), "http port")
  val level: Level = Level.ERROR
  override val levelFlag = flag("", level, "")

  override def modules = Seq(ServicesModule)

  override def postWarmup() {
    val service = ServicesModule.endpoints(injector).toService
    Http.serveAndAnnounce(s"zk!${ServicesModule.zk}/rest}", httpPort(), service)
    Await.ready(adminHttpServer)
  }
}
