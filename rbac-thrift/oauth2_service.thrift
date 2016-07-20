namespace java com.maogogo.rbac.thrift
#@namespace scala com.maogogo.rbac.thrift

include "rbac_models.thrift"

struct TSession {
  1: rbac_models.UserInfo userInfo
  2: optional rbac_models.Organization organization
  3: optional rbac_models.Employee employee
}

struct TAccessToken {
  1: string token
  2: optional string refreshToken
  3: optional string scope
  4: optional i64 expiresIn
  5: i64 createdAt  // Date
}

struct TAuthInfo {
  1: TSession session
  2: string clientId
  3: optional string scope
  4: optional string redirectUri
}

service OAuth2Service {

  bool validateClient(string clientId, string clientSecret, string grantType) throws (rbac_models.RbacServiceException ex)
  list<TSession> findUser(string username, string password) throws (rbac_models.RbacServiceException ex)
  list<TAccessToken> createAccessToken(TAuthInfo authInfo) throws (rbac_models.RbacServiceException ex)
  list<TAuthInfo> findAuthInfoByCode(string code) throws (rbac_models.RbacServiceException ex)
  list<TAuthInfo> findAuthInfoByRefreshToken(string refreshToken) throws (rbac_models.RbacServiceException ex)
  list<TSession> findClientUser(string clientId, string clientSecret, optional string scope) throws (rbac_models.RbacServiceException ex)
  list<TAccessToken> findAccessToken(string token) throws (rbac_models.RbacServiceException ex)
  list<TAuthInfo> findAuthInfoByAccessToken(TAccessToken accessToken) throws (rbac_models.RbacServiceException ex)
  list<TAccessToken> getStoredAccessToken(TAuthInfo authInfo) throws (rbac_models.RbacServiceException ex)
  list<TAccessToken> refreshAccessToken(TAuthInfo authInfo, string refreshToken) throws (rbac_models.RbacServiceException ex)

}
