package com.maogogo.rbac.modules

import com.twitter.inject.TwitterModule
import com.twitter.inject.Logging
import com.maogogo.rbac.common.modules.BaseConfigModule
import com.twitter.scrooge.ThriftService
import com.maogogo.rbac.thrift.HelloService
import com.maogogo.rbac.hello.HelloEndpoints

object ServicesModule extends TwitterModule with Logging with BaseConfigModule {

  override def configure: Unit = {
    bindSingleton[HelloService.FutureIface].toInstance(provideClient[HelloService.FutureIface]("hello"))
  }

  def endpoints(injector: com.twitter.inject.Injector) = {
    injector.instance[HelloEndpoints].endpoints
  }

}
