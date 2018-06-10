package u2fScalaExample.yubicoU2F

import com.yubico.u2f.U2F
import com.yubico.u2f.data.DeviceRegistration
import com.yubico.u2f.data.messages.{SignRequestData, SignResponse}
import com.yubico.u2f.exceptions.{DeviceCompromisedException, U2fBadInputException}
import u2fScalaExample.model._
import u2fScalaExample.storage.{RequestStorage, UserStorage}
import u2fScalaExample.Config.APP_ID

import scala.collection.JavaConverters._
import scala.concurrent.{Future, ExecutionContext}

object Authentication {
  import cats.data.Xor
  import cats.syntax.xor._

  val u2f = new U2F()

  def start(username: Username)(implicit ec: ExecutionContext): Future[SignRequestData] = for {
      currentRegistrations <- UserStorage.getRegistrations(username)
      requestData <- Future(u2f.startSignature(APP_ID, currentRegistrations.asJava))
      _ <- RequestStorage.put(requestData.getRequestId, requestData.toJson)
    } yield requestData

  def finish(username: Username, tokenResponse: String)(implicit ec: ExecutionContext): Future[Xor[U2FError, DeviceRegistration]] = {
    val auth = for {
      currentRegistrations <- UserStorage.getRegistrations(username)
      response <- Future(SignResponse.fromJson(tokenResponse))
      requestData <- popRequestData(response.getRequestId)
      deviceRegistration <- Future(u2f.finishSignature(requestData, response, currentRegistrations.asJava))
    } yield deviceRegistration.right

    auth.recover {
      case e: U2fBadInputException => U2FBadInputError().left
      case e: DeviceCompromisedException => DeviceCompromisedError().left
    }
  }

  private def popRequestData(requestId: String)(implicit ec: ExecutionContext): Future[SignRequestData] = for {
    storedRequestData <- RequestStorage.get(requestId)
    _ <- RequestStorage.remove(requestId)
    requestData <- Future(SignRequestData.fromJson(storedRequestData))
  } yield requestData

}
