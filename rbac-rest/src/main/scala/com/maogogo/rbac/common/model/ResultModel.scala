package com.maogogo.rbac.common.model

case class ResultModel[T](data: T, success: Boolean = true, msg: Option[String] = None, pages: Option[PagesModel] = None)

case class PagesModel(count: Int = 0, pageNum: Int = 1, pageSize: Int = 10, pageCount: Int = 0)
