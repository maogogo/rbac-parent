package com.maogogo.rbac.user

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject

class UserInfoServiceImpl @Inject() (dao: UserInfoDao, indexer: UserInfoIndexer) extends UserInfoService.FutureIface {

  def get(req: GetUserInfoReq): Future[Seq[UserInfo]] = {

    Future.value(Seq.empty)
  }

  def saveOrUpdate(userInfo: UserInfo, owner: String): Future[UserInfo] = {
    Future.value(null)
  }

  def delete(ids: Seq[String], owner: String): Future[Int] = {
    Future.value(-1)
  }

}