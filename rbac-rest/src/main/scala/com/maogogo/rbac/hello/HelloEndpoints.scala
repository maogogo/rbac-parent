package com.maogogo.rbac.hello

import javax.inject.Inject
import com.maogogo.rbac.thrift.HelloService
import io.finch._

class HelloEndpoints @Inject() (service: HelloService.FutureIface) {

  def endpoints() = test

  val test: Endpoint[String] = get("hello") {
    Ok("Hello")
  }
}
