package repositories

import com.google.inject.{Inject, Singleton}
import models.Post

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation that's based on mutable synchronized collection.
  *
  * @param executionContext execute program logic asynchronously, typically but not necessarily on a thread pool
  */
@Singleton
class PostPostRepositoryImpl @Inject()(implicit val executionContext: ExecutionContext) extends PostRepository {

  val postCollection: mutable.ArrayBuffer[Post] = mutable.ArrayBuffer.empty[Post]

  /** @inheritdoc*/
  override def create(post: Post): Future[Post] = Future.successful {
    synchronized {
      val newPost = post.copy(
        id = Some(postCollection.size + 1L)
      )
      postCollection += newPost
      newPost
    }
  }

  /** @inheritdoc*/
  override def find(id: Long): Future[Option[Post]] = Future.successful {
    postCollection.find(_.id.contains(id))
  }

  /** @inheritdoc*/
  override def findAll(): Future[List[Post]] = Future.successful {
    postCollection.toList
  }

  /** @inheritdoc*/
  override def update(post: Post): Future[Post] = synchronized {
    post.id match {
      case Some(id) =>
        find(id).flatMap {
          case Some(_) =>
            val foundPostIndex = postCollection.indexWhere(_.id == post.id)
            postCollection(foundPostIndex) = post
            Future.successful(post)
          case _ => Future.failed(new Exception(s"Not found post with id=$id"))
        }
      case _ => Future.failed(new Exception(s"Post's id wasn't provided."))
    }
  }

  /** @inheritdoc*/
  override def delete(id: Long): Future[Boolean] = Future.successful {
    synchronized {
      postCollection.indexWhere(_.id.contains(id)) match {
        case -1 => false
        case postIndex =>
          postCollection.remove(postIndex)
          true
      }
    }
  }
}