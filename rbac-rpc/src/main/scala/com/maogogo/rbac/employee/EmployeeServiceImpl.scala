package com.maogogo.rbac.employee

import com.maogogo.rbac.thrift._
import com.twitter.util.Future
import javax.inject.Inject
import com.maogogo.rbac.common.utils.UUID

class EmployeeServiceImpl @Inject() (dao: EmployeeDao, indexer: EmployeeIndexer) extends EmployeeService.FutureIface {

  def get(req: GetEmployeeReq): Future[Seq[Employee]] = {
    if (req.id.nonEmpty) {
      dao.findOne(req.id.getOrElse(""))
    } else if (req.words.nonEmpty) {
      indexer.query(req.words.getOrElse(""))
    } else {
      dao.findAll()
    }
  }

  def saveOrUpdate(req: Employee, owner: String): Future[Employee] = {
    val copy = if (req.userId.isEmpty) req.copy(userId = UUID.uuid()) else req

    for {
      x <- dao.saveOrUpdate(req)
      _ <- indexer.saveOrUpdate(req)
    } yield (if (x > 0) copy else req)
  }

  def delete(ids: Seq[String], owner: String): Future[Int] = dao.delete(ids)

}