package com.maogogo.rbac.organization

import javax.inject.Inject
import jp.sf.amateras.solr.scala._
import com.twitter.util.Future
import com.maogogo.rbac.thrift.Organization
import com.maogogo.rbac.common.utils.RegUtil
import com.twitter.inject.Logging
import com.maogogo.rbac.common.index.BaseIndexer
import org.slf4j.LoggerFactory

class OrganizationIndexer @Inject() (solrClient: SolrClient) extends BaseIndexer {

  val log = LoggerFactory.getLogger(getClass)

  val querys = Seq("organization_name", "organization_code", "parent_code")

  def saveOrUpdate(req: Organization): Future[Unit] = {

    val doc = Map(
      "id" -> req.id.getOrElse(""),
      "organization_name" -> req.organizationName,
      "organization_code" -> req.organizationCode,
      "parent_code" -> req.parentCode
    )

    Future.value(solrClient.add(doc))
  }

  def query(word: String): Future[Seq[Organization]] = {

    val params = getParams(word, querys)

    log.info("word : " + word)
    log.info("paramsQuery : " + params._1)
    log.info("paramsMap : " + params._2)

    Future.value(solrClient.query(params._1).getResultAsMap(params._2).documents.map { doc =>
      Organization(
        id = Some(doc("id").asInstanceOf[String]),
        organizationName = doc("organization_name").asInstanceOf[String],
        organizationCode = doc("organization_code").asInstanceOf[String],
        parentCode = doc("parent_code").asInstanceOf[String]
      )
    })

  }

}