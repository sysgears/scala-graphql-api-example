package repositories

import scala.concurrent.Future

/**
  * Simple CRUD repository which provides basic operations.
  */
trait Repository[T] {

  /**
    * Creates instance.
    *
    * @param item a new instance
    * @return created instance
    */
  def create(item: T): Future[T]

  /**
    * Returns instance by id.
    *
    * @param id an id of the instance
    * @return found instance
    */
  def find(id: Long): Future[Option[T]]

  /**
    * Returns a list of instances.
    *
    * @return list of instance
    */
  def findAll(): Future[List[T]]

  /**
    * Updates existing instance.
    *
    * @param item new instance
    * @return updated instance
    */
  def update(item: T): Future[T]

  /**
    * Delete existing instance by id.
    *
    * @param id an id of some instance
    * @return true/false result of deleting
    */
  def delete(id: Long): Future[Int]
}