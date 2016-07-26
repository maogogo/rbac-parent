package com.maogogo.rbac.role

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject

class RoleInfoServiceImpl @Inject() (dao: RoleInfoDao) extends RoleInfoService.FutureIface {

  def get(req: GetRoleInfoReq): Future[Seq[RoleInfo]] = {
    Future.value(Seq.empty)
  }

  def saveOrUpdate(roleInfo: RoleInfo, owner: String): Future[RoleInfo] = {
    Future.value(null)
  }

  def delete(ids: Seq[String] = Seq[String](), owner: String): Future[Int] = {
    Future.value(-1)
  }

}