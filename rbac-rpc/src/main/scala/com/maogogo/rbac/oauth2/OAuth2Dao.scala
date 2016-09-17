package com.maogogo.rbac.oauth2

import javax.inject.Inject
import com.twitter.finagle.exp._
import com.twitter.finagle.exp.mysql._
import com.maogogo.rbac.common.timer.TimeProvider
import com.maogogo.rbac.common.dao.BaseDao

class OAuth2Dao @Inject() (client: Client, timer: TimeProvider) extends BaseDao {

}