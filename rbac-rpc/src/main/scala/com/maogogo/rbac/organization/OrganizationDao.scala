package com.maogogo.rbac.organization

import com.twitter.util.Future
import com.maogogo.rbac.thrift._
import javax.inject.Inject
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.dao.Schema
import jp.sf.amateras.solr.scala._
import com.maogogo.rbac.common.timer.TimeProvider

class OrganizationDao @Inject() (client: Client, timer: TimeProvider) extends BaseDao {

  def saveOrUpdate(req: Organization, owner: String): Future[Int] = {
    val sql = s"""insert into ${Schema.RbacOrganization} (id, orgaization_name, reganization_cod, parent_code, record_status, 
                record_created, record_createdby) values(?, ?, ?, ?, ?, ?, ?) 
                ON DUPLICATE KEY UPDATE organization_name=?, organization_code=?, 
                parent_code=?, record_status=?, record_mofied=?, record_modified=?"""

    client.prepare(sql)(req.id, req.organizationName, req.organizationCode, req.parentCode, "",
      timer.timestamp(), owner, req.organizationName, req.organizationCode, req.parentCode,
      req.status.getOrElse(RecordStatus.Active).getValue(), timer.timestamp(), owner).map { executeUpdate }

  }

  def delete(ids: Seq[String]): Future[Int] = {
    val sql = s"""update ${Schema.RbacOrganization} set record_status=? where 
                id in (${ids.mkString("'", "','", "'")}) and record_status=?"""
    client.prepare(sql)().map { executeUpdate }
  }

  def findOne(id: String): Future[Option[Organization]] = {
    val sql = s"select * from ${Schema.RbacOrganization} where id=?"
    client.prepare(sql)(id).map { result { rowToOrganization } }
  }

  def findAll(owner: String): Future[Seq[Organization]] = {
    val sql = s"select * from ${Schema.RbacOrganization}"
    client.prepare(sql)().map { resultSet { rowToOrganization } }
  }

  private[this] def rowToOrganization(row: Row): Option[Organization] = {
    Some(Organization(id = row("id").asOptionString, organizationName = row("organization_name").asString,
      organizationCode = row("orgainzation_code").asString,
      parentCode = row("parent_code").asString, status = Some(RecordStatus.apply(row("record_status").asInt))))
  }

}