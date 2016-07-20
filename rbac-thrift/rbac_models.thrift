namespace java com.maogogo.rbac.thrift
#@namespace scala com.maogogo.rbac.thrift

enum RecordStatus {
  NO_STATUS	= 0
  ACTIVE	= 1
  DELETED	= 2
}

enum ExceptionCode {
  SUCCESS	= 200
  ERROR		= 500
}

struct RoleInfo {
  1: optional string id
  2: string roleName
}

struct UserInfo {
  1: optional string id
  2: string userName
  3: string userPasswd
  4: optional string cellPhone
  5: optional string email
}

struct Employee {
  1: string userId
}

struct Organization {
  1: optional string id
  2: string organizationName
  3: string organizationCode
  4: string parentCode
  5: optional i64 created
  6: optional string createdBy
  7: optional i64 modified
  8: optional string modifiedBy
  9: optional RecordStatus status
}

struct Privielge {
  1: optional string id
  2: string privilegeName
}

exception RbacServiceException {
  1: ExceptionCode code
  2: optional string msg
}
