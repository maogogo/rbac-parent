package com.maogogo.rbac.organization

import com.twitter.util.Future
import com.maogogo.rbac.thrift._
import javax.inject.Inject
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.dao.Schema
import jp.sf.amateras.solr.scala._

class OrganizationDao @Inject() (client: Client, indexer: OrganizationIndexer) extends BaseDao {

  def find(word: String): Future[Seq[Organization]] = {
    val sql = s"select * from ${Schema.RbacOrganization}"

    indexer.query(word)

    //    val params = Map(
    //      "phone" -> Seq("1", "2").map(_ + "*"))
    //
    //    val queryPattern = params.keys.map { k => s"(${k}:$$${k}$$)" }.mkString(" OR ")

    //    solrClient.add(Map("id" -> "003", "organization_name" -> "北京石景山714499258支行", "organization_code" -> "336677",
    //      "parent_code" -> "114488", "status" -> "99")).commit()
    //
    //    val result = solrClient.query("organization_name:$name$ or parent_code:$code$")
    //      .getResultAsMap(Map("name" -> "北京44", "code" -> "*22*"))
    //      .documents.map { doc =>
    //        Organization(
    //          id = Some(doc("id").asInstanceOf[String]),
    //          organizationName = doc("organization_name").asInstanceOf[String],
    //          organizationCode = doc("organization_code").asInstanceOf[String],
    //          parentCode = doc("parent_code").asInstanceOf[String]
    //        )
    //      }
    //Future.value(result)

    //client.prepare(sql)().map { resultSet { rowToOrganization } }
  }

  private[this] def rowToOrganization(row: Row): Option[Organization] = {
    Some(Organization(id = row("id").asOptionString, organizationName = row("organization_name").asString,
      organizationCode = row("orgainzation_code").asString,
      parentCode = row("parent_code").asString, status = Some(RecordStatus.apply(row("record_status").asInt))))
  }

}