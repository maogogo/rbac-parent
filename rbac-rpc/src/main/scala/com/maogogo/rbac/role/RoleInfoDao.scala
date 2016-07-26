package com.maogogo.rbac.role

import javax.inject.Inject
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.dao.Schema
import jp.sf.amateras.solr.scala._
import com.maogogo.rbac.common.timer.TimeProvider
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.util.Future
import com.maogogo.rbac.thrift._

class RoleInfoDao @Inject() (client: Client, timer: TimeProvider) extends BaseDao {

  def saveOrUpdate(req: RoleInfo, owner: String): Future[Int] = {
    val sql = s"""insert into ${Schema.RbacRoleInfo} (id, ) values(?, ?, ?, ?, ?, ?, ?) 
                ON DUPLICATE KEY UPDATE organization_name=?, organization_code=?, 
                parent_code=?, record_status=?, record_mofied=?, record_modified=?"""

    client.prepare(sql)(req.id, owner).map { executeUpdate }

  }

  def delete(ids: Seq[String]): Future[Int] = {
    val sql = s"""update ${Schema.RbacRoleInfo} set record_status=? where 
                id in (${ids.mkString("'", "','", "'")}) and record_status=?"""
    client.prepare(sql)().map { executeUpdate }
  }

  def findOne(id: String): Future[Option[RoleInfo]] = {
    val sql = s"select * from ${Schema.RbacRoleInfo} where id=?"
    client.prepare(sql)(id).map { result { rowToRoleInfo } }
  }

  def findAll(owner: String): Future[Seq[RoleInfo]] = {
    val sql = s"select * from ${Schema.RbacOrganization}"
    client.prepare(sql)().map { resultSet { rowToRoleInfo } }
  }

  private[this] def rowToRoleInfo(row: Row): Option[RoleInfo] = {
    Some(RoleInfo(id = row("id").asOptionString, roleName = row("role_name").asString))
  }

}