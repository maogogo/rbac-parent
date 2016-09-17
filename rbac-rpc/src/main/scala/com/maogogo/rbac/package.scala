package com.maogogo

import com.twitter.util.Future

package object rbac {

  implicit def optionToSeq[T](t: Future[Option[T]]): Future[Seq[T]] = t map {
    _ match {
      case Some(t) => Seq(t)
      case _ => Seq.empty[T]
    }
  }
}