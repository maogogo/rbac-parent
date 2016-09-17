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

enum Gender {
  UNKNOWN = 0
  BOY	= 1
  GIRL	= 2
}

enum Grade {
  UNKNOWN = 0
}

enum Education {
  UNKNOWN = 0
}

struct RoleInfo {
  1: optional string id
  2: string role_name
}

struct UserInfo {
  1: optional string id
  2: string user_name
  3: string user_passwd
  4: optional string cellPhone
  5: optional string email
  6: optional string cellPhone_active
  7: optional string email_active
  8: i64 last_login_time
  9: i32 error_times
  10: optional i64 created
  11: optional string created_by
  12: optional i64 modified
  13: optional string modified_by
  14: optional RecordStatus status
}

struct Employee {
  1: string userId
  2: optional string employee_name
  3: optional string employee_no
  4: optional string organization_id
  5: optional i64 birthday
  6: optional i64 entry_time
  7: optional i64 quit_time
  8: optional Grade grade
  9: optional Education education
  10: optional Gender gender
  11: optional i64 created
  12: optional string created_by
  13: optional i64 modified
  14: optional string modified_by
  15: optional RecordStatus status
}

struct Organization {
  1: optional string id
  2: string organization_name
  3: string organization_code
  4: string parent_code
  5: optional i64 created
  6: optional string created_by
  7: optional i64 modified
  8: optional string modified_by
  9: optional RecordStatus status
}

struct Privilege {
  1: optional string id
  2: string privilegeName
}

exception RbacServiceException {
  1: ExceptionCode code
  2: optional string msg
}
