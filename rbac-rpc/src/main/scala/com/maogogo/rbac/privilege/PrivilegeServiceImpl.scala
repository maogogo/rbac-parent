package com.maogogo.rbac.privilege

import com.maogogo.rbac.thrift._
import javax.inject.Inject
import com.twitter.util.Future

class PrivilegeServiceImpl @Inject() (dao: PrivilegeDao) extends PrivilegeService.FutureIface {

  def get(req: GetPrivilegeReq): Future[Seq[Privilege]] = {
    Future.value(Seq.empty)
  }

  def saveOrUpdate(privilege: Privilege, owner: String): Future[RoleInfo] = {
    Future.value(null)
  }

  def delete(ids: Seq[String] = Seq[String](), owner: String): Future[Int] = {
    Future.value(-1)
  }
}