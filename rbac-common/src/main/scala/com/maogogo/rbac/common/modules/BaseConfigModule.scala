package com.maogogo.rbac.common.modules

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.google.inject._
import org.slf4j.LoggerFactory
import com.twitter.scrooge.ThriftService
import com.twitter.finagle.ThriftMux

trait BaseConfigModule {

  //lazy val zkEndpoints = provideConfig().getString("zookeepers.endpoints")
  lazy val zk: String = provideConfig().getString("rpc.zookeepers")
  def zook(s: String) = s"zk!${zk}${s}"
  private[this] def zookClient(s: String) = s"zk2!${zk}${s}"

  def provideServices(injector: com.twitter.inject.Injector): Map[String, ThriftService] = null

  def services(injector: com.twitter.inject.Injector) = {
    val rand = new scala.util.Random

    provideServices(injector).map { kv =>
      val (name, service) = kv
      val anounce = s"${zook(name)}!" + Math.abs(rand.nextInt)
      ThriftMux.server.serveIface(":*", service).announce(anounce)
    }.toSeq
  }

  def provideClient[T: Manifest](s: String): T = {
    ThriftMux.client.newIface[T](zookClient(s))
  }

  private[this] val env = {
    val envProperty = System.getProperty("RBAC_ENV")
    val envSystem = if (envProperty == null) System.getenv("RBAC_ENV") else envProperty
    val env = if (envSystem == null) "dev" else envSystem
    env + ".conf"
  }

  @Provides @Singleton
  def provideConfig(): Config = {
    //ogger info s"LOADING CONFIG FROM: ${env}"
    ConfigFactory load env
  }

  def provideRpcName(s: String): String = {
    provideConfig().getString(s"rpc.name.${s}")
  }

}
