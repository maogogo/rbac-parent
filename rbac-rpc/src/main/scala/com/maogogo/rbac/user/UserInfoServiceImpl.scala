package com.maogogo.rbac.user

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject
import com.maogogo.rbac.common.utils.UUID

class UserInfoServiceImpl @Inject() (dao: UserInfoDao, indexer: UserInfoIndexer) extends UserInfoService.FutureIface {

  def get(req: GetUserInfoReq): Future[Seq[UserInfo]] = {

    Future.value(Seq.empty)
  }

  def saveOrUpdate(userInfo: UserInfo, owner: String): Future[UserInfo] = {

    val copy = if (userInfo.id.isDefined) userInfo else userInfo.copy(id = Some(UUID.uuid()))

    for {
      _userInfo <- if (userInfo.id.isDefined) dao.findOne(userInfo.id.getOrElse("")) else Future.value(Some(copy))
      _ = if (_userInfo.isEmpty) throw new RbacServiceException(ExceptionCode.Error, Some("not found userinfo by id "))

    } yield _userInfo

    //dao.findOne(id)
    //userInfo.copy(id, userName, userPasswd, cellPhone, email, cellPhoneActive, emailActive, lastLoginTime, errorTimes, created, createdBy, modified, modifiedBy, status, _passthroughFields)
    Future.value(null)
  }

  private[this] def save(): Future[UserInfo] = {
    
    

    Future.value(null)
  }
  
  private[this] def update: Future[UserInfo] = {
    Future.value(null)
  }

  def delete(ids: Seq[String], owner: String): Future[Int] = {
    Future.value(-1)
  }

}