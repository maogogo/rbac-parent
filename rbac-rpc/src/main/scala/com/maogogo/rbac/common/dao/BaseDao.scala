package com.maogogo.rbac.common.dao

import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.thrift._
import java.util.Date
import java.util.TimeZone

trait BaseDao {

  implicit def anyToParameter(p: Option[Any]): Parameter = Parameter.unsafeWrap(p.getOrElse(null))

  type Bytes = Array[Byte]

  private[this] def notMatch[T](): PartialFunction[Value, Option[T]] = {
    case NullValue => None
    case x =>
      throw new RbacServiceException(ExceptionCode.Error, Some("内部数据解析错误:" + x))
  }

  def result[T](convert: Row => Option[T]): PartialFunction[Any, Option[T]] = {
    case rs: ResultSet => rs.rows.headOption.flatMap(convert)
    case _ => None
  }

  def resultSet[T](convert: Row => Option[T]): PartialFunction[Any, Seq[T]] = {
    case rs: ResultSet => rs.rows.map(convert).filter(_.isDefined).map(_.get)
    case _ => Seq.empty[T]
  }

  def executeUpdate(r: Result): Int = r match {
    case OK(rows, _, _, _, _) => rows.toInt
    case _ => -1
  }

  implicit class RichValueOption(v: Option[Value]) {

    lazy val timestampValueLocal = new TimestampValue(TimeZone.getDefault(), TimeZone.getDefault())

    def asOptionInt[T](method: Int => T) = as[Int, T]({
      case IntValue(v) => Option(method(v))
      case ByteValue(v) => Option(method(v))
    })

    def asOptionInt = asOptionInt[Int](x => x)

    def asInt = asOptionInt[Int](x => x).getOrElse(0)

    def asLong[T](method: Long => T) = as[Long, T]({
      case LongValue(v) => Option(method(v))
      case IntValue(v) => Option(method(v))
    })

    def asLong = asLong[Long](x => x)

    def asTimestamp[T](method: Long => T) = as[Long, T]({
      case timestampValueLocal(v) => Option(method(v.getTime))
    })

    def asTimestamp = asTimestamp[Long](x => x)

    def asOptionString[T](method: String => T) = as[String, T]({ case StringValue(v) => Option(method(v)) })

    def asOptionString = asOptionString[String](x => x)

    def asString = asOptionString[String](x => x).getOrElse("")

    def asDate[T](method: Date => T) = as[Date, T]({ case DateValue(v) => Option(method(v)) })

    def asDate = asDate[Date](x => x)

    def asBytes[T](method: Bytes => T) = as[Bytes, T]({ case RawValue(_, _, _, v) => Option(method(v)) })

    def asBytes = asBytes[Bytes](x => x)

    private[this] def as[V, T](pf: PartialFunction[Any, Option[T]]): Option[T] = (v map (pf orElse notMatch)).flatten

    private def notMatch[T](): PartialFunction[Value, Option[T]] = {
      case NullValue => None
      case x =>
        throw new RbacServiceException(ExceptionCode.Error, Some("内部数据解析错误:" + x))
    }
  }

}
