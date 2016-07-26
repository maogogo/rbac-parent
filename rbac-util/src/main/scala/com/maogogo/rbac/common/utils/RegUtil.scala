package com.maogogo.rbac.common.utils

import scala.util.matching.Regex

object RegUtil {

  val IntegerPattern = "\\d+".r

  def matches(str: String, reg: Regex) = str match {
    case reg(_*) => true
    case _ => false
  }

}