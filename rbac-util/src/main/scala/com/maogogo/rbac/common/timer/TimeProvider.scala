package com.maogogo.rbac.common.timer

trait TimeProvider {
  def now(): Long

  def sqlDate(): java.sql.Date

  def date(): java.util.Date

  def timestamp(): java.sql.Timestamp
}

class DefaultTimeProvider extends TimeProvider {

  def now(): Long = System.currentTimeMillis

  def sqlDate(): java.sql.Date = new java.sql.Date(now)

  def date(): java.util.Date = new java.util.Date

  def timestamp(): java.sql.Timestamp = new java.sql.Timestamp(now)

}