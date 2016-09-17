package com.maogogo.rbac.common.utils

import com.google.common.hash.Hashing
import com.google.common.base.Charsets

object UUID {

  def uuid(): String = {
    Hashing.murmur3_32.hashString(java.util.UUID.randomUUID().toString, Charsets.UTF_8) + "-" +
      Hashing.murmur3_32.hashString(java.util.UUID.randomUUID().toString, Charsets.UTF_8)
  }

  def random(b: Int = 6): String = {
    def exp(r: Int = 1): Int = if (r == 1) 10 else (10 * exp(r - 1))
    val k = exp(b - 1)
    (scala.util.Random.nextInt(k * 9 - 1) + k).toString
  }

}