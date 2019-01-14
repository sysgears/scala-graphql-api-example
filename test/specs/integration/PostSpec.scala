package specs.integration

import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, POST, contentAsString, contentType, status}
import play.mvc.Http
import specs.TestHelper

class PostSpec extends TestHelper {

  "GraphiQL route" must {

    "return 200 OK with the GraphiQL page" in {
      val request = FakeRequest(GET, "/")
      val home = appController.graphiql.apply(request)

      status(home) mustBe Http.Status.OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("GraphiQL")
    }
  }

  "Queries" must {
    "returns all posts" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(posts)

      val result = appController.graphqlBody.apply(request)

      contentAsString(result) must include("data")
    }

    "finds post by id" in {

    }
  }

  "Mutations" must {
    "add new post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(addPost)

      val result = appController.graphqlBody.apply(request)

      contentAsString(result) must include("data")
    }

    "updates an existing post" in {

    }

    "deletes the post" in {

    }
  }
}
