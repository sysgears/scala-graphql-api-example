package resolvers

import models.Item
import models.Item.GraphQL.ItemInput

import scala.concurrent.Future

class ItemResolver {

  def items: Future[List[Item]] = ???

  def addItem (input: ItemInput): Future[Item] = ???

}
