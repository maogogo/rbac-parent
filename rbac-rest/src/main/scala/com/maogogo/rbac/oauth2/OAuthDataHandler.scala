package com.maogogo.rbac.oauth2

import com.maogogo.rbac._
import com.maogogo.rbac.thrift._
import com.twitter.finagle.oauth2._
import com.twitter.util.Future
import javax.inject.Inject

class OAuthDataHandler @Inject() (service: OAuth2Service.FutureIface) extends DataHandler[TSession] {

  def createAccessToken(authInfo: AuthInfo[TSession]): Future[AccessToken] =
    service.createAccessToken(authInfo).map { _.headOption.get }

  def findAccessToken(token: String): Future[Option[AccessToken]] =
    service.findAccessToken(token).map { _.headOption.map { toAccessToken } }

  def findAuthInfoByAccessToken(accessToken: AccessToken): Future[Option[AuthInfo[TSession]]] =
    service.findAuthInfoByAccessToken(accessToken).map { _.headOption.map { toAuthInfo } }

  def findAuthInfoByCode(code: String): Future[Option[AuthInfo[TSession]]] =
    service.findAuthInfoByCode(code).map { _.headOption.map { toAuthInfo } }

  def findAuthInfoByRefreshToken(refreshToken: String): Future[Option[AuthInfo[TSession]]] =
    service.findAuthInfoByRefreshToken(refreshToken).map { _.headOption.map { toAuthInfo } }

  def findClientUser(clientId: String, clientSecret: String, scope: Option[String]): Future[Option[TSession]] =
    service.findClientUser(clientId, clientSecret, scope).map { _.headOption }

  def findUser(username: String, password: String): Future[Option[TSession]] =
    service.findUser(username, password).map { _.headOption }

  def getStoredAccessToken(authInfo: AuthInfo[TSession]): Future[Option[AccessToken]] =
    service.getStoredAccessToken(authInfo).map { _.headOption.map { toAccessToken } }

  def refreshAccessToken(authInfo: AuthInfo[TSession], refreshToken: String): Future[AccessToken] =
    service.refreshAccessToken(authInfo, refreshToken).map { _.headOption.get }

  def validateClient(clientId: String, clientSecret: String, grantType: String): Future[Boolean] =
    service.validateClient(clientId, clientSecret, grantType)

}