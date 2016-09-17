package com.maogogo.rbac.user

import javax.inject.Inject
import jp.sf.amateras.solr.scala._
import com.twitter.util.Future
import com.maogogo.rbac.common.index.BaseIndexer
import org.slf4j._
import com.maogogo.rbac.thrift.UserInfo

class UserInfoIndexer @Inject() (solrClient: SolrClient) extends BaseIndexer {

  val log = LoggerFactory.getLogger(getClass)

  val querys = Seq("user_name", "cellphone", "email")

  def saveOrUpdate(req: UserInfo): Future[Unit] = {

    val doc = Map(
      "id" -> req.id.getOrElse(""),
      "user_name" -> req.userName,
      "cellphone" -> req.cellPhone,
      "email" -> req.email
    )

    Future.value(solrClient.add(doc))
  }

  def query(word: String): Future[Seq[UserInfo]] = {

    val params = getParams(word, querys)

    log.info("word : " + word)
    log.info("paramsQuery : " + params._1)
    log.info("paramsMap : " + params._2)

    Future.value(solrClient.query(params._1).getResultAsMap(params._2).documents.map { doc =>
      UserInfo(
        id = Some(doc("id").asInstanceOf[String]),
        userName = doc("user_name").asInstanceOf[String],
        userPasswd = "",
        cellPhone = Some(doc("cellphone").asInstanceOf[String]),
        email = Some(doc("email").asInstanceOf[String]),
        lastLoginTime = doc("last_login_time").asInstanceOf[Long],
        errorTimes = doc("error_times").asInstanceOf[Int]
      )
    })

  }

}