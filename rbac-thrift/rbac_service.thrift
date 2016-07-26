namespace java com.maogogo.rbac.thrift
#@namespace scala com.maogogo.rbac.thrift

include "rbac_models.thrift"

struct GetOrganizationReq {
  1: optional string id
  2: optional string words
  3: string owner
}

struct GetUserInfoReq {
  1: optional string id
  2: optional string words
  3: string owner
}

struct GetRoleInfoReq {
  1: optional string id
  2: optional string words
  3: string owner
}

struct GetEmployeeReq {
  1: optional string id
  2: optional string words
  3: string owner
}

struct GetPrivilegeReq {
  1: optional string id
  2: optional string words
  3: string owner
}

service OrganizationService {

  string hi(string name) throws (rbac_models.RbacServiceException ex)
  
  list<rbac_models.Organization> get(GetOrganizationReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.Organization saveOrUpdate(rbac_models.Organization organization, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}

service UserInfoService {

  list<rbac_models.UserInfo> get(GetUserInfoReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.UserInfo saveOrUpdate(rbac_models.UserInfo userInfo, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}

service RoleInfoService {

  list<rbac_models.RoleInfo> get(GetRoleInfoReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.RoleInfo saveOrUpdate(rbac_models.RoleInfo roleInfo, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}

service EmployeeService {

  list<rbac_models.Employee> get(GetEmployeeReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.Employee saveOrUpdate(rbac_models.Employee employee, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}

service PrivilegeService {

  list<rbac_models.Privilege> get(GetPrivilegeReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.RoleInfo saveOrUpdate(rbac_models.Privilege privilege, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}



