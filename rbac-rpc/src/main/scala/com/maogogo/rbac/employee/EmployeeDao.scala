package com.maogogo.rbac.employee

import com.twitter.util.Future
import com.maogogo.rbac.thrift._
import javax.inject.Inject
import com.maogogo.rbac.common.dao.BaseDao
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.dao.Schema
import jp.sf.amateras.solr.scala._
import com.maogogo.rbac.common.timer.TimeProvider

class EmployeeDao @Inject() (client: Client, timer: TimeProvider) extends BaseDao {

  def saveOrUpdate(req: Employee): Future[Int] = {
    val sql = s"insert into ${Schema.RbacOrganization} "
    Future.value(-1)
  }

  def delete(ids: Seq[String]): Future[Int] = {
    val sql = s"""update ${Schema.RbacOrganization} set record_status=? where 
                id in (${ids.mkString("'", "','", "'")}) and record_status=?"""
    client.prepare(sql)().map { executeUpdate }
  }

  def findOne(id: String): Future[Option[Employee]] = {
    val sql = s"select * from ${Schema.RbacEmployee} where id=?"
    client.prepare(sql)().map { result { rowToEmployee } }
  }

  def findAll(): Future[Seq[Employee]] = {
    val sql = s"select * from ${Schema.RbacEmployee}"
    client.prepare(sql)().map { resultSet { rowToEmployee } }
  }

  private[this] def rowToEmployee(row: Row): Option[Employee] =
    Some(
      Employee(userId = row("user_id").asString, employeeName = row("employee_name").asOptionString,
        employeeNo = row("employee_no").asOptionString, organizationId = row("organization_id").asOptionString)
    )

}
