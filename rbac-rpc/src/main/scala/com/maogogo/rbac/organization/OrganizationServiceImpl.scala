package com.maogogo.rbac.organization

import com.maogogo.rbac.thrift.OrganizationService
import com.twitter.util.Future

class OrganizationServiceImpl extends OrganizationService.FutureIface {

  def hi(name: String): Future[String] = {
    println("===>>>>>>>>>hello : " + name)
    Future.value("Hello World")
  }

}
