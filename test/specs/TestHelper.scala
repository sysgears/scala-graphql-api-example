package specs

import java.util.concurrent.TimeUnit

import akka.stream.Materializer
import akka.util.Timeout
import controllers.AppController
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}


trait TestHelper extends PlaySpec with GuiceOneAppPerSuite with Injecting with PreparedInput{

  implicit lazy val executionContext: ExecutionContext = inject[ExecutionContext]
  implicit lazy val materializer: Materializer = app.materializer
  implicit lazy val timeout: Timeout = Timeout(20, TimeUnit.SECONDS)
  lazy val appController: AppController = inject[AppController]

  def await[T](asyncFunc: => Future[T]): T = Await.result[T](asyncFunc, Duration.Inf)

}
