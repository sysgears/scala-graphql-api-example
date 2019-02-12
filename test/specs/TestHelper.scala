package specs

import java.util.concurrent.TimeUnit

import akka.stream.Materializer
import akka.util.Timeout
import controllers.AppController
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

import scala.concurrent.ExecutionContext

/**
  * Prepares tools for easy writing and execution of tests.
  * Injecting dependencies, filling the database with initial data, defines timeouts for requests.
  */
trait TestHelper extends PlaySpec with GuiceOneAppPerSuite with Injecting with PreparedInput {

  /**
    * Implicit definition the execution context for asynchronous operations.
    */
  implicit lazy val executionContext: ExecutionContext = inject[ExecutionContext]

  /**
    * Creates Akka Materializer for this application.
    */
  implicit lazy val materializer: Materializer = app.materializer

  /**
    * Defines timeouts for http requests.
    */
  implicit lazy val timeout: Timeout = Timeout(20, TimeUnit.SECONDS)

  /**
    * Injects instance of AppController.
    */
  lazy val appController: AppController = inject[AppController]
}
