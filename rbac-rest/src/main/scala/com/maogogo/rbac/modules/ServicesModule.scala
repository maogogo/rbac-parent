package com.maogogo.rbac.modules

import com.twitter.inject.TwitterModule
import com.twitter.inject.Logging
import com.maogogo.rbac.thrift._
import com.twitter.finagle._
import com.maogogo.rbac.organization.OrganizationEndpoints
import com.google.inject.Injector
import com.google.inject._
import com.typesafe.config._
import com.twitter.finagle.service.TimeoutFilter
import com.twitter.util.Duration
import com.twitter.finagle.factory.TimeoutFactory
import com.twitter.conversions.time._
import com.twitter.finagle.loadbalancer.Balancers

object ServicesModule extends TwitterModule with Logging {

  private[this] val namespace = "/rbac"
  lazy val zk: String = provideConfig().getString("zookeeper")
  private[this] def zook(s: String) = s"zk2!${zk}${namespace}${s}"

  private[this] val env = {
    val envProperty = System.getProperty("RBAC_ENV")
    val envSystem = if (envProperty == null) System.getenv("RBAC_ENV") else envProperty
    val env = if (envSystem == null) "dev" else envSystem
    env + ".conf"
  }

  override def configure: Unit = {
    bindSingleton[OrganizationService.FutureIface].toInstance(
      providesClient[OrganizationService.FutureIface]("/organization")
    )

  }

  private[this] def providesClient[T: Manifest](s: String): T = {
    ThriftMux.client
      .withLoadBalancer(Balancers.heap())
      .withRequestTimeout(provideConfig().getInt("thrift.timeout").milliseconds)
      //.configured(TimeoutFilter.Param(Duration.Top))
      //.configured(TimeoutFactory.Param(Duration.Top))
      .newIface[T](zook(s))
  }

  def endpoints(injector: com.twitter.inject.Injector) = {
    injector.instance[OrganizationEndpoints].endpoints
  }

  @Provides @Singleton
  def provideConfig(): Config = {
    //ogger info s"LOADING CONFIG FROM: ${env}"
    ConfigFactory load env
  }

}