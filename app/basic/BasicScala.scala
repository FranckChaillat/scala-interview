package com.particeep.test.basic


/**
 * This is basic language questions so don't use external library or build in function
 */
object BasicScala {

  /**
   * Encode parameter in url format
   *
   * Example:
   *
   * input  : Map("sort_by" -> "name", "order_by" -> "asc", "user_id" -> "12")
   * output : "?sort_by=name&order_by=asc&user_id=12"
   *
   * input  : Map()
   * output : ""
   */
  def encodeParamsInUrl(params: Map[String, String]): String =
    if(params.nonEmpty)
      s"?${params.map {case (k,v) => s"$k=$v"}.mkString("&")}"
    else
      ""

  /**
   * Test if a String is an email
   */
  def isEmail(maybeEmail: String): Boolean =
    maybeEmail matches "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"

  /**
   * Compute i ^ n
   *
   * Example:
   *
   * input : (i = 2, n = 3) we compute 2^3 = 2x2x2
   * output : 8
   *
   * input : (i = 99, n = 38997)
   * output : 1723793299
   */
  def power(i: Int, n: Int): Double = {
    val result = (1 to   scala.math.abs(n)).fold(1) { (acc, _) => acc * i }
    if(n < 0)
      1d / result
    else result
  }


}
