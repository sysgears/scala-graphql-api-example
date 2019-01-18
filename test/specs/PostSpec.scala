package specs

import models.Post
import play.api.test.FakeRequest
import play.api.test.Helpers.{GET, POST, contentAsString, contentType, status}
import play.mvc.Http
import spray.json._

/**
  * Contains all test suites and specs.
  */
class PostSpec extends TestHelper {

  /**
    * Imports implicit for conversion Post in JSON format to Post object.
    */
  import models.PostJsonProtocol._

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
  /**
    * Contains test specs for queries.
    */
  "Queries" must {
    "returns all posts" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(posts)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      val postList = result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("posts").convertTo[List[Post]]

      postList.isEmpty mustEqual false
    }

    "finds post by id" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(findPost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      val foundPost = result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("findPost").asJsObject.convertTo[Post]

      foundPost.id mustBe defined
      foundPost.id.get mustEqual 1
      foundPost.title mustEqual "First post"
      foundPost.content mustEqual "First content"
    }
  }

  /**
    * Contains test specs for mutations.
    */
  "Mutations" must {
    "add new post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(addPost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      val newPost = result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("addPost").asJsObject.convertTo[Post]

      newPost.id mustBe defined
      newPost.title mustEqual "New post"
      newPost.content mustEqual "I created a post"
    }

    "updates an existing post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(updatePost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      val updatedPost = result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("updatePost").asJsObject.convertTo[Post]

      updatedPost.id mustBe defined
      updatedPost.title mustEqual "New title"
      updatedPost.content mustEqual "X#X#X#X#X#"
    }

    "deletes the post" in {
      val request = FakeRequest(POST, "/graphql").withHeaders(("Content-Type", "application/json")).withBody(deletePost)

      val result = contentAsString(appController.graphqlBody.apply(request))

      result must include("data")
      result mustNot include("errors")

      val isDelete = result.parseJson.asJsObject.fields("data")
        .asJsObject.fields("deletePost").convertTo[Boolean]

      isDelete mustEqual true
    }
  }
}
