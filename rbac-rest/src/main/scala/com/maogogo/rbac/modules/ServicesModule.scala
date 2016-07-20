package com.maogogo.rbac.modules

import com.twitter.inject.TwitterModule
import com.twitter.inject.Logging
import com.maogogo.rbac.thrift._
import com.twitter.finagle._
import com.maogogo.rbac.organization.OrganizationEndpoints
import com.google.inject.Injector

object ServicesModule extends TwitterModule with Logging {

  private[this] val namespace = "/rbac"
  lazy val zk: String = "localhost:2181!/dev"
  private[this] def zook(s: String) = s"zk2!${zk}${namespace}${s}"

  override def configure: Unit = {
    bindSingleton[OrganizationService.FutureIface].toInstance(
      ThriftMux.client.newIface[OrganizationService.FutureIface](zook("/organization")))
  }

  def endpoints(injector: com.twitter.inject.Injector) = {
    injector.instance[OrganizationEndpoints].endpoints
  }
  
}