
package com.maogogo.rbac.organization

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject
import org.slf4j.LoggerFactory
import com.maogogo.rbac.common.utils.UUID

class OrganizationServiceImpl @Inject() (dao: OrganizationDao, indexer: OrganizationIndexer) extends OrganizationService.FutureIface {

  val log = LoggerFactory.getLogger(getClass)

  def hi(name: String): Future[String] = {

    log.info("========>>>" + name)
    //    dao.find(name).map { x =>
    //      x.foreach { println }
    //      log.info("========name>>>" + x.size)
    //      "hello : " + x.size
    //    }

    Future.value("Hello:" + name)
  }

  def get(req: GetOrganizationReq): Future[Seq[Organization]] = {

    if (req.id.nonEmpty) {
      dao.findOne(req.id.getOrElse(""))
    } else if (req.words.nonEmpty) {
      indexer.query(req.words.getOrElse(""))
    } else {
      dao.findAll("")
    }

  }

  def saveOrUpdate(req: Organization, owner: String): Future[Organization] = {

    val copy = if (req.id.isEmpty) req.copy(id = Some(UUID.uuid())) else req

    for {
      x <- dao.saveOrUpdate(req, owner)
      _ <- indexer.saveOrUpdate(req)
    } yield (if (x > 0) copy else req)

  }

  def delete(ids: Seq[String], owner: String): Future[Int] = dao.delete(ids)

}
