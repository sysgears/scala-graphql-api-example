package specs

import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, contentAsString, contentType, status}
import play.mvc.Http

/**
  * Contains test for checks route.
  */
class GraphiQLRouteSpec extends TestHelper {

  "GraphiQL route" must {

    /**
      * Checks that when executing a GET request on '/' return page with GraphiQL.
      */
    "return 200 OK with the GraphiQL page" in {
      val request = FakeRequest(GET, "/")
      val home = appController.graphiql.apply(request)

      status(home) mustBe Http.Status.OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("GraphiQL")
    }
  }
}
