package graphql.resolvers

import com.google.inject.Inject
import models.Post
import repositories.PostRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * A resolver class that contains all resolver methods for the Post model.
  *
  * @param postRepository an repository which provides basic operations for Post entity
  * @param executionContext execute program logic asynchronously, typically but not necessarily on a thread pool
  */
class PostResolver @Inject()(postRepository: PostRepository,
                             implicit val executionContext: ExecutionContext) {

  /**
    * Returns a list of all posts.
    *
    * @return list of posts
    */
  def posts: Future[List[Post]] = postRepository.findAll()

  /**
    * Add and save new Post instance.
    *
    * @param title post title
    * @param content post content
    * @return created Post instance
    */
  def addPost(title: String, content: String): Future[Post] = postRepository.create(Post(None, title, content))

  /**
    * Finds Post by id.
    *
    * @param id an id of the Post
    * @return found Post instance
    */
  def findPost(id: Long): Future[Option[Post]] = postRepository.find(id)

  /**
    * Updates existing post.
    *
    * @param post post for update
    * @return updated Post instance
    */
  def updatePost(post: Post): Future[Post] = postRepository.update(post)

  /**
    * Deletes existing post.
    *
    * @param id a post id
    * @return boolean result
    */
  def deletePost(id: Long): Future[Boolean] = postRepository.delete(id)
}
