package com.maogogo.rbac.user

import javax.inject.Inject
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.timer.TimeProvider
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.util.Future
import com.maogogo.rbac.thrift._
import com.maogogo.rbac.common.dao.Schema

class UserInfoDao @Inject() (client: Client, timer: TimeProvider) extends BaseDao {

  def saveOrUpdate(req: UserInfo, owner: String): Future[Int] = {

    val sql = s"""INSERT INTO ${Schema.RbacRoleInfo} (id, user_name, user_password, cellphone, cellphone_active, email, 
            email_active, last_login_time, error_times, record_status, record_created, record_createdby, record_notes) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE user_name=?, user_password=?, cellphone=?, 
            cellphone_active=?, email=?, email_active=?, last_login_time=?, 
            error_times=?, record_status=?, record_modified=?, record_modifiedby=? """

    client.prepare(sql)(req.id, req.userName, req.userPasswd, req.cellPhone, req.cellPhoneActive, req.email, req.emailActive,
      req.lastLoginTime, req.errorTimes, req.status, req.created, req.createdBy, "",
      req.userName, req.userPasswd, req.cellPhone, req.cellPhoneActive, req.email, req.emailActive, req.lastLoginTime,
      req.errorTimes, req.status, req.modified, req.modifiedBy).map { executeUpdate }
  }

  def findOne(id: String): Future[Option[UserInfo]] = {
    val sql = s"select * from ${Schema.RbacUserInfo} where id=?"
    client.prepare(sql)(id).map { result { rowToUserInfo } }
  }

  def findAll(): Future[Seq[UserInfo]] = {
    val sql = s"select * from ${Schema.RbacUserInfo}"
    client.prepare(sql)().map { resultSet { rowToUserInfo } }
  }

  def rowToUserInfo(row: Row): Option[UserInfo] = {
    Some(UserInfo(id = row("id").asOptionString, userName = row("user_name").asString,
      userPasswd = row("user_password").asString, cellPhone = row("cellphone").asOptionString,
      email = row("email").asOptionString, cellPhoneActive = row("cellphone_active").asOptionString,
      emailActive = row("email_active").asOptionString, lastLoginTime = row("last_login_time").asLong,
      errorTimes = row("error_times").asInt))
  }

}