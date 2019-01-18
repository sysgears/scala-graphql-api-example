package repositories

import models.Post

import scala.concurrent.Future

/**
  * Simple CRUD repository which provides basic operations.
  */
trait PostRepository {

  /**
    * Creates instance.
    *
    * @param post a new instance
    * @return created instance
    */
  def create(post: Post): Future[Post]

  /**
    * Returns instance by id.
    *
    * @param id an id of the instance
    * @return found instance
    */
  def find(id: Long): Future[Option[Post]]

  /**
    * Returns a list of instances.
    *
    * @return list of instance
    */
  def findAll(): Future[List[Post]]

  /**
    * Updates existing instance.
    *
    * @param post new instance
    * @return updated instance
    */
  def update(post: Post): Future[Post]

  /**
    * Delete existing instance by id.
    *
    * @param id an id of some instance
    * @return true/false result of deleting
    */
  def delete(id: Long): Future[Boolean]
}