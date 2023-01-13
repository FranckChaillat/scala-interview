package async

import com.particeep.test.async.WhatsWrong2.EnterpriseCEO
import com.particeep.test.async.{AsyncBasic, CEO, Enterprise, WhatsWrong2}
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.PlaySpec

class AsyncTest extends PlaySpec with ScalaFutures {
  "AsyncBasic" should {
    "compute the sum of the two webservice call result" in {
      whenReady(AsyncBasic.compute("1")) { maybesum =>
        maybesum mustBe Some(1099)
      }

      whenReady(AsyncBasic.compute("2")) { maybesum =>
        maybesum mustBe Some(21)
      }
    }

    "return None if both service did'nt answered correctly" in {
      whenReady(AsyncBasic.compute("6")) { maybesum =>
        maybesum mustBe None
      }
    }
  }

  "WhatsWrong2" should {

    "getCEOAndEnterprise should return None if no CEO nor Enterprise has been found" in {
      whenReady(WhatsWrong2.getCEOAndEnterprise(Some("42")).value) { maybeResult =>
        maybeResult mustBe None
      }
    }

    "getCEOAndEnterprise should return an object with both ceo and enterprise value if found" in {
      whenReady(WhatsWrong2.getCEOAndEnterprise(Some("1")).value) { maybeResult =>
        maybeResult mustBe Some(
          EnterpriseCEO(
            Some(CEO("1", "Mark", "Zuckerberg")),
            Some(Enterprise("1", "Google", "1"))
          )
        )
      }
    }

    "getCEOAndEnterprise should return an object with CEO but without enterprise since it's not known by the DAO" in {
      whenReady(WhatsWrong2.getCEOAndEnterprise(Some("3")).value) { maybeResult =>
        maybeResult mustBe Some(
          EnterpriseCEO(
            Some(CEO("3", "Oliver", "Blume")),
            None)
        )
      }
    }

    "getCEOAndEnterprise should return an object with Enterprise but without CEO since it's not known by the DAO" in {
      whenReady(WhatsWrong2.getCEOAndEnterprise(Some("4")).value) { maybeResult =>
        maybeResult mustBe Some(
          EnterpriseCEO(
            None,
            Some(Enterprise("3", "TikTok", "4")))
        )
      }
    }
  }
}
