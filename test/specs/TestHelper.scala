package specs

import java.util.concurrent.TimeUnit

import akka.stream.Materializer
import akka.util.Timeout
import controllers.AppController
import graphql.resolvers.PostResolver
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}


trait TestHelper extends PlaySpec with GuiceOneAppPerSuite with Injecting with BeforeAndAfter with PreparedInput {

  implicit lazy val executionContext: ExecutionContext = inject[ExecutionContext]
  implicit lazy val postResolver: PostResolver = inject[PostResolver]
  implicit lazy val materializer: Materializer = app.materializer
  implicit lazy val timeout: Timeout = Timeout(20, TimeUnit.SECONDS)
  lazy val appController: AppController = inject[AppController]

  before {
    await(postResolver.addPost(title = "First post", content = "First content"))
    await(postResolver.addPost(title = "Second post", content = "Second content"))
    await(postResolver.addPost(title = "Third post", content = "Third content"))
  }

  def await[T](asyncFunc: => Future[T]): T = Await.result[T](asyncFunc, Duration.Inf)
}
