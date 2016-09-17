package com.maogogo.rbac.employee

import javax.inject.Inject
import jp.sf.amateras.solr.scala._
import com.maogogo.rbac.common.index.BaseIndexer
import org.slf4j.LoggerFactory
import com.twitter.util.Future
import com.maogogo.rbac.thrift._

class EmployeeIndexer @Inject() (solrClient: SolrClient) extends BaseIndexer {

  val log = LoggerFactory.getLogger(getClass)

  val querys = Seq("employee_name", "employee_name")

  def saveOrUpdate(req: Employee): Future[Unit] = {

    val doc = Map(
      "id" -> req.userId,
      "employee_name" -> req.employeeName,
      "employee_no" -> req.employeeNo
    )

    Future.value(solrClient.add(doc))
  }

  def query(word: String): Future[Seq[Employee]] = {

    val params = getParams(word, querys)

    log.info("word : " + word)
    log.info("paramsQuery : " + params._1)
    log.info("paramsMap : " + params._2)

    Future.value(solrClient.query(params._1).getResultAsMap(params._2).documents.map { doc =>
      Employee(
        userId = doc("user_id").asInstanceOf[String],
        employeeName = Some(doc("employee_name").asInstanceOf[String]),
        employeeNo = Some(doc("employee_no").asInstanceOf[String])
      )
    })

  }

}