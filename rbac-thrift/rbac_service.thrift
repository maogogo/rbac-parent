namespace java com.maogogo.rbac.thrift
#@namespace scala com.maogogo.rbac.thrift

include "rbac_models.thrift"

struct GetOrganizationReq {
  1: optional string id
  2: optional string key_words
}

service OrganizationService {

  string hi(string name) throws (rbac_models.RbacServiceException ex)
  
  list<rbac_models.Organization> get(GetOrganizationReq req) throws (rbac_models.RbacServiceException ex)
  
  rbac_models.Organization saveOrUpdate(rbac_models.Organization organization, string owner) throws (rbac_models.RbacServiceException ex)
  
  i32 delete(list<string> ids, string owner) throws (rbac_models.RbacServiceException ex)
  
}
