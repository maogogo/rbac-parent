package com.maogogo.rbac.hello

import com.maogogo.rbac.thrift.HelloService
import com.twitter.util.Future

class HelloServiceImpl extends HelloService.FutureIface {

  def sayHello(s: String): Future[String] = Future.value("Hello World")

}
