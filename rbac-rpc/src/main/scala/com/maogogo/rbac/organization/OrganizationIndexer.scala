package com.maogogo.rbac.organization

import javax.inject.Inject
import jp.sf.amateras.solr.scala._
import com.twitter.util.Future
import com.maogogo.rbac.thrift.Organization
import com.maogogo.rbac.common.utils.RegUtil
import com.twitter.inject.Logging

class OrganizationIndexer @Inject() (solrClient: SolrClient) extends Logging {

  def saveOrUpdate(docs: Any): Future[Unit] = {
    Future.value(solrClient.add(docs))
  }

  def query(word: String): Future[Seq[Organization]] = {
    val words = word.split("\\w+").filter { _.nonEmpty }.map {
      case x if RegUtil.matches(x, RegUtil.IntegerPattern) => s"*${x}*"
      case y => y
    }.zipWithIndex

    val querys = Seq("organization_name", "organization_code", "parent_code")
    //val querys = Map("organization_name" -> words, "organization_code" -> words, "parent_code" -> words)

    val paramsQuery = querys.flatMap { q =>
      words.map { f =>
        s"(${q}:$$${q}_${f._2}$$)"
      }
    }.mkString(" OR ")

    val paramsMap = querys.flatMap { q =>
      words.map { f =>
        s"${q}_${f._2}" -> f._1
      }
    }.toMap

    info("word : " + word)
    info("paramsQuery : " + paramsQuery)
    info("paramsMap : " + paramsMap)

    Future.value(solrClient.query(paramsQuery).getResultAsMap(paramsMap).documents.map { doc =>
      Organization(
        id = Some(doc("id").asInstanceOf[String]),
        organizationName = doc("organization_name").asInstanceOf[String],
        organizationCode = doc("organization_code").asInstanceOf[String],
        parentCode = doc("parent_code").asInstanceOf[String]
      )
    })

  }

}