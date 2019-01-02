package repositories

import models.Item

import scala.concurrent.Future

trait ItemRepository {

  def create(item: Item): Future[Item]

  def find(id: Long): Future[Option[Item]]

  def findAll(): Future[List[Item]]

  def update(item: Item): Future[Item]

  def delete(id: Long): Future[Int]
}