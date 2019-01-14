package specs

trait PreparedInput {

  lazy val addPost = "{ \"mutation\": \"{ addPost( \"title\":\"Some Post\" \"content\": \"First post\"){ id title content } }\" }"
  lazy val updatePost = "{ \"mutation\": \"{ updatePost( \"id\":2 \"title\": \"new title\" \"content\": \"X#X#X#X#X#\"){ id title content } }\" }"
  lazy val findPost = "{ \"query\": \"{ findPost( \"id\":1 ){ id title content} }\" }"
  lazy val deletePost = "{ \"mutation\": \"{ deletePost( \"id\": 3 ) }\" }"
  lazy val posts = "{ \"query\": \"{ posts { id title content } }\"}"



}
