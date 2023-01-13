package com.particeep.test.async

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.chaining.scalaUtilChainingOps

case class CEO(id: String, first_name: String, last_name: String)
case class Enterprise(id: String, name: String, ceo_id: String)

object CEODao {
  val ceos = List(
    CEO("1", "Mark", "Zuckerberg"),
    CEO("2", "Sundar", "Pichai"),
    CEO("3", "Oliver", "Blume")
  )

  def byId(id: String): Future[Option[CEO]] = Future(ceos.find(_.id == id))
}

object EnterpriseDao {
  val enterprises = List(
    Enterprise("1", "Google", "1"),
    Enterprise("2", "Facebook", "2"),
    Enterprise("3", "TikTok", "4")
  )

  def byId(id:String): Future[Option[Enterprise]] = Future(enterprises.find(_.id == id))
  def byCEOId(ceo_id: String): Future[Option[Enterprise]] = Future(enterprises.find(_.ceo_id == ceo_id))
}

object WhatsWrong2 {

  final case class EnterpriseCEO(ceo: Option[CEO], enterprise: Option[Enterprise]) {
    def isEmpty: Boolean = ceo.isEmpty && enterprise.isEmpty
  }

  //Review this code. What could be done better ? How would you do it ?
  /*def getCEOAndEnterprise(ceo_id: Option[String]): Future[(Option[CEO], Option[Enterprise])] = {
    for {
      ceo        <- CEODao.byId(ceo_id.get)
      enterprise <- EnterpriseDao.byCEOId(ceo_id.get)
    } yield {
      (ceo, enterprise)
    }
  }*/

  /**
   * What i would do:
   * 1 is avoid using tuple as public function output
   * 2 Instead add a domain entity (EnterpriseCEO), it also improve the readability
   * 3 To improve the readability even more i would use the OptionT type of cats that allows to avoid writing Future[Option]
   * 4 Obviously avoid using the .get() on an option to preserve safety
   * 5 The snake case on the input parameter doesn't match with the caml case everywhere else
   * 6 didn't do it but generally speaking i don't use option as input parameters,
   *  i tend to think that None case should be handled by the caller
   * */
  def getCEOAndEnterprise(ceoId: Option[String]): OptionT[Future, EnterpriseCEO] = {
    ceoId.map { ceoId =>
      for {
        ceo        <- CEODao.byId(ceoId)
        enterprise <- EnterpriseDao.byCEOId(ceoId)
      } yield EnterpriseCEO(ceo, enterprise)
    }.traverse(identity)
      .map(_.filterNot(_.isEmpty))
      .pipe(OptionT.apply)
  }
}
