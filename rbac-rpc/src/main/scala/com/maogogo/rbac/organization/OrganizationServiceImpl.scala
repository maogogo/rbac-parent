package com.maogogo.rbac.organization

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject
import org.slf4j.LoggerFactory

class OrganizationServiceImpl @Inject() (dao: OrganizationDao) extends OrganizationService.FutureIface {

  val log = LoggerFactory.getLogger(getClass)

  def hi(name: String): Future[String] = {

    log.info("========>>>" + name)
    dao.find(name).map { x =>
      x.foreach { println }
      log.info("========name>>>" + x.size)
      "hello : " + x.size
    }

    //    println("===>>>>>>>>>hello : " + name)
    //    Future.value("Hello World")
  }

  def get(req: GetOrganizationReq): Future[Seq[Organization]] = {
    Future.value(Seq.empty[Organization])
  }

  def saveOrUpdate(organization: Organization, owner: String): Future[Organization] = {
    Future.value(organization)
  }

  def delete(ids: Seq[String], owner: String): Future[Int] = {
    Future.value(0)
  }

}
