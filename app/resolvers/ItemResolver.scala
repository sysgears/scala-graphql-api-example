package resolvers

import com.google.inject.Inject
import models.Item
import models.Item.GraphQL.{ItemInput, ItemsPayload}
import repositories.ItemRepository

import scala.concurrent.{ExecutionContext, Future}

class ItemResolver @Inject()(itemRepository: ItemRepository,
                             implicit val executionContext: ExecutionContext) {

  def items: Future[ItemsPayload] = for {
    result <- itemRepository.findAll()
  } yield ItemsPayload(result)

  def addItem (input: ItemInput): Future[Item] = itemRepository.create(Item(1,input.description))
}
