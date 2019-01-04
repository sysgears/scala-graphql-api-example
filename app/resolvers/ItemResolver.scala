package resolvers

import com.google.inject.Inject
import models.Item
import repositories.ItemRepository

import scala.concurrent.{ExecutionContext, Future}

class ItemResolver @Inject()(itemRepository: ItemRepository,
                             implicit val executionContext: ExecutionContext) {

  def items: Future[List[Item]] = itemRepository.findAll()

  def addItem(description: String): Future[Item] = itemRepository.create(Item(1, description))

  def findItem(id: Long) = itemRepository.find(id)
}
