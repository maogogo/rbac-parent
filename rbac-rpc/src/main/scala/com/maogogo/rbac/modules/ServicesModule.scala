package com.maogogo.rbac.modules

import com.google.inject._
import com.twitter.finagle._
import com.maogogo.rbac.thrift._
import com.twitter.inject.{ TwitterModule, Logging }
import com.maogogo.rbac.organization._
import com.typesafe.config._

object ServicesModule extends TwitterModule with Logging {

  private[this] lazy val namespace = "/rbac"
  private[this] lazy val zk: String = provideConfig().getString("zookeeper")
  private[this] def zook(s: String) = s"zk!${zk}${namespace}${s}"

  private[this] val env = {
    val envProperty = System.getProperty("RBAC_ENV")
    val envSystem = if (envProperty == null) System.getenv("RBAC_ENV") else envProperty
    val env = if (envSystem == null) "dev" else envSystem
    env + ".conf"
  }

  override def configure: Unit = {
    bindSingleton[OrganizationService.FutureIface].to[OrganizationServiceImpl]
  }

  private[this] def provideServices(injector: com.twitter.inject.Injector) = Map( //s"${namespace}/oauth2" -> injector.instance[OAuth2Service.FutureIface],
    s"/organization" -> injector.instance[OrganizationService.FutureIface]
  )

  def services(injector: com.twitter.inject.Injector) = {
    val rand = new scala.util.Random
    provideServices(injector).map { kv =>
      val (name, service) = kv
      val anounce = s"${zook(name)}!" + Math.abs(rand.nextInt)

      ThriftMux.server.serveIface(":*", service).announce(anounce)
    }.toSeq
  }

  @Provides @Singleton
  def provideConfig(): Config = {
    //ogger info s"LOADING CONFIG FROM: ${env}"
    ConfigFactory load env
  }
}
