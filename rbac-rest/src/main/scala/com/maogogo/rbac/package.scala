package com.maogogo

import org.json4s._
import org.json4s.native.Serialization
import org.json4s.jackson._
import io.finch._
import com.twitter.util.Future
import io.finch.Output
import com.twitter.util.Try
import com.twitter.finagle.oauth2._
import java.util.Date
import com.maogogo.rbac.thrift._
import com.maogogo.rbac.common.model.ResultModel

package object rbac {

  implicit val formats = Serialization.formats(NoTypeHints)

  implicit def decodeJson[A: Manifest](implicit formats: Formats): DecodeRequest[A] =
    DecodeRequest.instance(input => Try(JsonMethods.parse(input, false).extract[A]))

  implicit def encodeJson[A <: AnyRef](implicit formats: Formats): EncodeResponse[A] =
    EncodeResponse.fromString[A]("application/json") { writeJson(_) }

  private[this] def writeJson[A <: AnyRef](a: A)(implicit formats: Formats): String = {
    val ex = a match {
      case r: RbacServiceException => r
      case t: Throwable => RbacServiceException(ExceptionCode.Error, Some(t.getMessage))
      case e => e
    }

    val cleaned = Extraction.decompose(ex)(formats).removeField {
      case JField("_passthroughFields", _) => true
      case _ => false
    }

    Serialization.write(removeHeadAndTail(cleaned))
  }

  private def removeHeadAndTail(input: JValue): JValue = {
    input match {
      case JObject(List(("tail" | "head", rest))) => removeHeadAndTail(rest)
      case x => x
    }
  }

  implicit def toAuthInfo(ai: TAuthInfo) = AuthInfo(ai.session, ai.clientId, ai.scope, ai.redirectUri)

  implicit def toAccessToken(at: TAccessToken) = AccessToken(at.token, at.refreshToken, at.scope, at.expiresIn, new Date(at.createdAt))

  implicit def toTAuthInfo(ai: AuthInfo[TSession]) = TAuthInfo(ai.user, ai.clientId, ai.scope, ai.redirectUri)

  implicit def toTAccessToken(at: AccessToken) = TAccessToken(at.token, at.refreshToken, at.scope, at.expiresIn, at.createdAt.getTime)

  type Result[T] = com.maogogo.rbac.common.model.ResultModel[T]

  type Pages = com.maogogo.rbac.common.model.PagesModel

  implicit class WrappableFuture[T](f: Future[T]) {
    def wrapped() = f.map { x => ResultModel(data = x) }
  }
}