package specs.integration

import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, POST, contentAsString, contentType, status}
import play.mvc.Http
import specs.TestHelper
import spray.json._

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

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      result.parseJson.asJsObject.fields("data")
      .asJsObject.fields("posts")
    }

    "finds post by id" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(findPost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      result.parseJson.asJsObject.fields("data")
      .asJsObject.fields("findPost")
    }
  }

  "Mutations" must {
    "add new post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(addPost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("addPost")
    }

    "updates an existing post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(updatePost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("updatePost")
    }

    "deletes the post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(deletePost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("deletePost") mustEqual true
    }
  }
}
