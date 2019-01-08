package repositories

import scala.concurrent.Future

/**
  * Simple CRUD repository which provides basic operations.
  */
trait Repository[T] {

  /**
    * Creates Item instance.
    *
    * @param item a Item instance
    * @return created Item instance
    */
  def create(item: T): Future[T]

  /**
    * Returns Item by id.
    *
    * @param id an id of the Item
    * @return found Item instance
    */
  def find(id: Long): Future[Option[T]]

  /**
    * Returns a list of items.
    *
    * @return list of items
    */
  def findAll(): Future[List[T]]

  /**
    * Updates existing Item.
    *
    * @param item new Item instance
    * @return updated Item instance
    */
  def update(item: T): Future[T]

  /**
    * Delete existing Item instance by id.
    *
    * @param id an id of the Item
    * @return a number of deleted Item
    */
  def delete(id: Long): Future[Int]
}