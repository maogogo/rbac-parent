package com.maogogo.rbac.oauth2

import com.twitter.util.Future
import com.maogogo.rbac.thrift._

class OAuth2ServiceImpl extends OAuth2Service.FutureIface {

  def createAccessToken(authInfo: TAuthInfo): Future[Seq[TAccessToken]] = {
    Future.value(null)
  }

  def findAccessToken(token: String): Future[Seq[TAccessToken]] = {
    Future.value(null)
  }

  def findAuthInfoByAccessToken(accessToken: TAccessToken): Future[Seq[TAuthInfo]] = {
    Future.value(null)
  }

  def findAuthInfoByCode(code: String): Future[Seq[TAuthInfo]] = {
    Future.value(null)
  }

  def findAuthInfoByRefreshToken(refreshToken: String): Future[Seq[TAuthInfo]] = {
    Future.value(null)
  }

  def findClientUser(clientId: String, clientSecret: String, scope: Option[String]): Future[Seq[TSession]] = {
    Future.value(null)
  }

  def findUser(username: String, password: String): Future[Seq[TSession]] = {
    Future.value(null)
  }

  def getStoredAccessToken(authInfo: TAuthInfo): Future[Seq[TAccessToken]] = {
    Future.value(null)
  }

  def refreshAccessToken(authInfo: TAuthInfo, refreshToken: String): Future[Seq[TAccessToken]] = {
    Future.value(null)
  }

  def validateClient(clientId: String, clientSecret: String, grantType: String): Future[Boolean] = {
    Future.value(false)
  }

}