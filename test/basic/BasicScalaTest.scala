package com.particeep.test.basic

import com.particeep.test.basic.Refactoring.File
import org.scalatestplus.play.PlaySpec

import scala.util.{Success, Try}

class BasicScalaTest extends PlaySpec {


  "BasicScala" should {
    "encode non empty param url from map object" in {
      BasicScala.encodeParamsInUrl(Map("key1" -> "value1", "key2" -> "value2")) mustBe "?key1=value1&key2=value2"
      BasicScala.encodeParamsInUrl(Map("sort_by" -> "name", "order_by" -> "asc", "user_id" -> "12"))
        .mustBe("?sort_by=name&order_by=asc&user_id=12")
    }

    "encode empty map object or empty values" in {
      BasicScala.encodeParamsInUrl(Map()) mustBe ""
      BasicScala.encodeParamsInUrl(Map("foo" -> "")) mustBe "?foo="
    }
  }

  "BasicScala" should {
    "test email" in {
      BasicScala.isEmail("jean@particeep.com") mustBe true
      BasicScala.isEmail("jean_particeep.com") mustBe false
    }
  }

  "BasicScala" should {
    "compute the power of two number" in {
      BasicScala.power(2, 3) mustBe 8
      BasicScala.power(7, 3) mustBe 343
      BasicScala.power(99, 38997) mustBe 1723793299
      BasicScala.power(3, -2) mustBe 0.1111111111111111
      BasicScala.power(2, -2) mustBe 0.25
      BasicScala.power(4, 0) mustBe 1
      BasicScala.power(4, 1) mustBe 4

    }
  }

  "ComputeAverage" should {
    "work on non empty list" in {
      ComputeAverage.average(List(1,2,3,4)) mustBe Some(10d/4d)
      ComputeAverage.average(List(-1,2,-3)) mustBe Some(-2d/3d)
      ComputeAverage.average(List(1, 10, 16)) mustBe Some(9)
    }

    "as well as on empty list (shouldn't throw any error)" in {
      ComputeAverage.average(List.empty) mustBe None
    }
  }

  "Refactoring" should {
    "get categories from file list and ignore the null category" in {
      val result = Refactoring.getCategories(
        List(
          File("filename1", "publicCategory"),
          File("filename2", "publicCategory"),
          File("filename1", "privateCategory"),
          File("filename1", null),
        )
      )
      result.length mustBe 2
      result.contains("publicCategory") mustBe true
      result.contains("privateCategory") mustBe true
    }

    "should work if an empty list is given as input" in {
      val result = Try(Refactoring.getCategories(null))
      result mustBe Success(List())
    }

  }
}
