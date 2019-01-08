package repositories

import com.google.inject.Inject
import models.Item

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation that's based on mutable synchronized collection.
  */
class ItemRepository @Inject()(implicit val executionContext: ExecutionContext) extends Repository[Item] {

  val itemCollection: mutable.ArrayBuffer[Item] = mutable.ArrayBuffer.empty[Item]

  /** @inheritdoc */
  override def create(item: Item): Future[Item] = Future.successful {
    synchronized {
      val newItem = item.copy(
        id = {
          val allIds = itemCollection.map(_.id)
          if (allIds.nonEmpty) allIds.max + 1L else 1L
        }
      )
      itemCollection += newItem
      newItem
    }
  }

  /** @inheritdoc */
  override def find(id: Long): Future[Option[Item]] = Future.successful {
      itemCollection.find(_.id == id)
  }

  /** @inheritdoc */
  override def findAll(): Future[List[Item]] = Future.successful {
      itemCollection.toList
  }

  /** @inheritdoc */
  override def update(item: Item): Future[Item] = synchronized {
    find(item.id).flatMap {
      case Some(foundItem) =>
        val updatedItem = foundItem.copy()
        val foundItemIndex = itemCollection.indexWhere(_.id == item.id)

        itemCollection(foundItemIndex) = updatedItem

        Future.successful(updatedItem)
      case _ => Future.failed(new Exception(s"Not found item with id=${item.id}"))
    }
  }

  /** @inheritdoc */
  override def delete(id: Long): Future[Int] = Future.successful {
    synchronized {
      val initialCollectionLength = itemCollection.length

      val resultingLength = itemCollection.indexWhere(_.id == id) match {
        case -1 => initialCollectionLength
        case itemIndex =>
          itemCollection.remove(itemIndex)
          initialCollectionLength - 1
      }
      initialCollectionLength - resultingLength
    }
  }
}