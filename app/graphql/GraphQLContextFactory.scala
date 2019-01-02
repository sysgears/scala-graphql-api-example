package graphql

import com.google.inject.Inject
import resolvers.ItemResolver

import scala.concurrent.ExecutionContext

class GraphQLContextFactory @Inject()(val itemResolver: ItemResolver,
                                      implicit val executionContext: ExecutionContext) {

  def createContextForRequest() = GraphQLContext(
    itemResolver,
    executionContext
  )
}

case class GraphQLContext(itemResolver: ItemResolver,
                          executionContext: ExecutionContext)
