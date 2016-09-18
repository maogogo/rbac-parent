package com.maogogo.rbac.modules

import com.twitter.inject.TwitterModule
import com.twitter.inject.Logging
import com.maogogo.rbac.common.modules.BaseConfigModule
import com.maogogo.rbac.thrift.HelloService
import com.twitter.finagle.ThriftMux
import com.twitter.scrooge.ThriftService

object ServicesModule extends TwitterModule with Logging with BaseConfigModule {

  override def configure: Unit = {

  }

  override def provideServices(injector: com.twitter.inject.Injector): Map[String, ThriftService] = Map(
    s"${provideRpcName("hello")}" -> injector.instance[HelloService.FutureIface]
  )

}
