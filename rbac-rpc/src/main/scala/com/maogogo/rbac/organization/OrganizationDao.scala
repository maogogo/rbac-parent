package com.maogogo.rbac.organization

import com.twitter.util.Future
import com.maogogo.rbac.thrift._
import javax.inject.Inject
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.dao.Schema

class OrganizationDao @Inject() (client: Client) extends BaseDao {

  def find(): Future[Seq[Organization]] = {
    val sql = s"select * from ${Schema.RbacOrganization}"
    client.prepare(sql)().map { resultSet { rowToOrganization } }
  }

  private[this] def rowToOrganization(row: Row): Option[Organization] = {
    Some(Organization(id = row("id").asOptionString, organizationName = row("organization_name").asString,
      organizationCode = row("orgainzation_code").asString,
      parentCode = row("parent_code").asString, status = Some(RecordStatus.apply(row("record_status").asInt))))
  }

}