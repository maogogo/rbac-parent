package com.maogogo.rbac.organization

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject

class OrganizationServiceImpl @Inject() (dao: OrganizationDao) extends OrganizationService.FutureIface {

  def hi(name: String): Future[String] = {
    dao.find().map { x =>
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
