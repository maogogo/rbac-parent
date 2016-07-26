package com.maogogo.rbac.common.index

import com.maogogo.rbac.common.utils.RegUtil

trait BaseIndexer {

  def getParams(word: String, querys: Seq[String]) = {

    val words = word.split("\\s+").filter { _.nonEmpty }.map {
      case x if RegUtil.matches(x, RegUtil.IntegerPattern) => s"*${x}*"
      case y => y
    }.zipWithIndex

    val paramsQuery = querys.flatMap { q =>
      words.map { f =>
        s"(${q}:$$${q}_${f._2}$$)"
      }
    }.mkString(" OR ")

    val paramsMap = querys.flatMap { q =>
      words.map { f =>
        s"${q}_${f._2}" -> f._1
      }
    }.toMap

    (paramsQuery, paramsMap)
  }

}