package com.maogogo.rbac.organization

import com.google.inject._
import com.maogogo.rbac.thrift._
import io.finch._
import com.twitter.util.Future

class OrganizationEndpoints @Inject() (service: OrganizationService.FutureIface) {

  def endpoints = hi

  val hi: Endpoint[String] = get("hi") {

    println("service ===>>>" + service)

    Ok(service.hi("Toan"))
  }
}