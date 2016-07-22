package com.maogogo.rbac

import java.net.InetSocketAddress
import com.twitter.util.Await
import com.google.inject.Guice
import com.maogogo.rbac.modules.ServicesModule
import com.twitter.finagle.Http
import com.twitter.inject.server.TwitterServer
import com.twitter.logging.Level
import com.twitter.logging.Logging.LevelFlaggable
import com.maogogo.rbac.filters.LoggingFilter

object Main extends TwitterServer {

  lazy val config = ServicesModule.provideConfig()

  override val adminPort = flag("admin.port", new InetSocketAddress(config.getInt("admin.port")), "")
  val httpPort = flag("http.port", new InetSocketAddress(config.getInt("http.port")), "http port")
  val level: Level = Level.ERROR
  override val levelFlag = flag("", level, "")

  lazy val sign = s"""
${"*" * 80}
    REST: ${adminPort} ${httpPort} started
${"*" * 80} """

  override def modules = Seq(ServicesModule)

  override def postWarmup() {
    val service = ServicesModule.endpoints(injector).toService
    val filter = new LoggingFilter()
    Http.serveAndAnnounce(s"zk!${ServicesModule.zk}/rest}", httpPort(), filter andThen service)
    info(sign)
    Await.ready(adminHttpServer)
  }
}
