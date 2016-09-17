package com.maogogo.rbac.filters

import com.twitter.inject.Logging
import com.twitter.finagle.http._
import com.twitter.finagle._
import com.twitter.util.Future

class LoggingFilter extends SimpleFilter[Request, Response] with Logging {

  def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    info(s"\n ==> ${request.method} ${request.path}")
    service.apply(request)
  }
}