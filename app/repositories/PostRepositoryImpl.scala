package repositories

import com.google.inject.{Inject, Singleton}
import errors.{AlreadyExists, AmbigousResult, NotFound}
import models.Post
import modules.AppDatabase

import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of repository for 'Post' entity.
  *
  * @param database         instance of database
  * @param executionContext execute program logic asynchronously, typically but not necessarily on a thread pool
  */
@Singleton
class PostRepositoryImpl @Inject()(val database: AppDatabase,
                                   implicit val executionContext: ExecutionContext) extends PostRepository {

  /**
    * Specific database
    */
  val db = database.db

  /**
    * Specific database profile
    */
  val profile = database.profile

  import profile.api._


  def postQuery = TableQuery[Post.Table]

  /**
    * Creates instance.
    *
    * @param post a new instance
    * @return created instance
    */
  override def create(post: Post): Future[Post] = db.run {
    Actions.create(post)
  }

  /**
    * Returns instance by id.
    *
    * @param id an id of the instance
    * @return found instance
    */
  override def find(id: Long): Future[Option[Post]] = db.run {
    Actions.find(id)
  }

  /**
    * Returns a list of instances.
    *
    * @return list of instance
    */
  override def findAll(): Future[List[Post]] = db.run {
    Actions.findAll()
  }

  /**
    * Updates existing instance.
    *
    * @param post new instance
    * @return updated instance
    */
  override def update(post: Post): Future[Post] = db.run {
    Actions.update(post)
  }

  /**
    * Delete existing instance by id.
    *
    * @param id an id of some instance
    * @return true/false result of deleting
    */
  override def delete(id: Long): Future[Boolean] = db.run {
    Actions.delete(id)
  }

  /**
    * Provides implementation for CRUD operations with 'Post' entity.
    */
  object Actions {

    def create(post: Post): DBIO[Post] =
      for {
        maybePost <- if (post.id.isEmpty) DBIO.successful(None) else find(post.id.get)
        maybePostId <- maybePost match {
          case Some(value) => DBIO.failed(AlreadyExists(s"Already exists post with id = ${post.id}"))
          case _ => postQuery returning postQuery.map(_.id) += post
        }
        maybePost <- find(maybePostId)
        post <- maybePost match {
          case Some(value)=> DBIO.successful(value)
          case _ => DBIO.failed(AmbigousResult(s"Failed to save post [post=$post]"))
        }
      } yield post


    def find(id: Long): DBIO[Option[Post]] = for {
      maybePost <- postQuery.filter(_.id === id).result
      post <- if (maybePost.lengthCompare(2) < 0) DBIO.successful(maybePost.headOption) else DBIO.failed(AmbigousResult(s"Several posts with the same id = $id"))
    } yield post


    def findAll(): DBIO[List[Post]] = for {
      posts <- postQuery.result
    } yield posts.toList

    def update(post: Post): DBIO[Post] = for {
      maybeId <- if (post.id.isDefined) DBIO.successful(post.id.get) else DBIO.failed(NotFound(s"Not found 'id' in the [post=$post]"))
      count <- postQuery.filter(_.id === maybeId).update(post)
      _ <- count match {
        case 0 => DBIO.failed(NotFound(s"Not found post with id=${post.id.get}"))
        case _ => DBIO.successful(())
      }
    } yield post

    def delete(id: Long): DBIO[Boolean] = for {
      maybeDelete <- postQuery.filter(_.id === id).delete
      isDelete = if (maybeDelete == 1) true else false
    } yield isDelete
  }

}