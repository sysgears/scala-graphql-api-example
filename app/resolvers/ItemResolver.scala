package resolvers

import com.google.inject.Inject
import models.Item
import repositories.ItemRepository

import scala.concurrent.{ExecutionContext, Future}

/**
  * A resolver class that contains all resolver methods for the Item model.
  *
  * @param itemRepository an repository which provides basic operations for Item entity
  * @param executionContext execute program logic asynchronously, typically but not necessarily on a thread pool
  */
class ItemResolver @Inject()(itemRepository: ItemRepository,
                             implicit val executionContext: ExecutionContext) {

  /**
    * Returns a list of all items.
    *
    * @return list of items
    */
  def items: Future[List[Item]] = itemRepository.findAll()

  /**
    * Allows to add and save new Item instance.
    *
    * @param description it's one of the field of the Item entity
    * @return created Item instance
    */
  def addItem(description: String): Future[Item] = itemRepository.create(Item(1, description))

  /**
    * Finds Item by id.
    *
    * @param id an id of the Item
    * @return found Item instance
    */
  def findItem(id: Long): Future[Option[Item]] = itemRepository.find(id)
}
