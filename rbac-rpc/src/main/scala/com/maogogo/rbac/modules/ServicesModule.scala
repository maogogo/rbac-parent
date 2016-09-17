package com.maogogo.rbac.modules

import com.google.inject._
import com.twitter.finagle._
import com.maogogo.rbac.thrift._
import com.twitter.inject.{ TwitterModule, Logging }
import com.maogogo.rbac.organization._
import com.typesafe.config._
import com.twitter.finagle.exp.Mysql
import com.twitter.finagle.client.DefaultPool
import com.twitter.finagle.exp.mysql.{ Client, Transactions }
import com.maogogo.rbac.common.timer._
import com.twitter.util.Future
import com.twitter.util.Duration
import jp.sf.amateras.solr.scala._
import com.maogogo.rbac.user.UserInfoIndexer
import com.maogogo.rbac.user.UserInfoDao
import com.maogogo.rbac.user.UserInfoServiceImpl

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

    bindSingleton[TimeProvider].to[DefaultTimeProvider]

    bindSingleton[OrganizationIndexer]
    bindSingleton[UserInfoIndexer]

    bindSingleton[OrganizationDao]
    bindSingleton[UserInfoDao]

    bindSingleton[OrganizationService.FutureIface].to[OrganizationServiceImpl]
    bindSingleton[UserInfoService.FutureIface].to[UserInfoServiceImpl]

  }

  private[this] def provideServices(injector: com.twitter.inject.Injector) = Map( //s"${namespace}/oauth2" -> injector.instance[OAuth2Service.FutureIface],
    s"/organization" -> injector.instance[OrganizationService.FutureIface],
    s"/userinfo" -> injector.instance[UserInfoService.FutureIface]
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

  @Provides @Singleton
  private[this] def getConnection(@Inject() config: Config): Client with Transactions = {
    val client = Mysql.client
      .withCredentials(config.getString("mysql.username"), config.getString("mysql.password"))
      .configured(DefaultPool.Param(
        low = 5,
        high = Int.MaxValue,
        idleTime = Duration.Top,
        bufferSize = 0,
        maxWaiters = Int.MaxValue
      ))

    client.withDatabase(config.getString("mysql.database")).newRichClient(config.getString("mysql.host"))
  }

  @Provides @Singleton
  def provideSolrClient(): SolrClient = {
    new SolrClient(provideConfig().getString("solr.client"))
  }

}