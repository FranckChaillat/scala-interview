package com.particeep.test.basic

/**
 * Compute the average of the list
 *
 * ex : [1, 10, 16] -> 9
 */
object ComputeAverage {
  def average(l: List[Double]): Option[Double] =
    if(l.isEmpty) None else Some(l.sum / l.length)

}
