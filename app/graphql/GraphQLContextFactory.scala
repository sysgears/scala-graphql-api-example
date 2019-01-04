package graphql

import com.google.inject.Inject
import resolvers.ItemResolver

import scala.concurrent.ExecutionContext

/**
  * Creates a GraphQLContext instance with an information about an HTTP request with a GraphQL query, that should be
  * executed in this context.
  *
  * The goal of this component is to split the initialization of a GraphQLContext into two steps:
  * 1. Inject all resolvers, via dependency injection only once, when this factory is injected into a controller.
  * 2. Create a new instance of GraphQLContext each time a new HTTP request arrives and besides all repos/services from step 1,
  * inject a request object into the context.
  *
  * @param itemResolver a resolver class that contains all resolver methods for the Item model.
  */
class GraphQLContextFactory @Inject()(val itemResolver: ItemResolver,
                                      implicit val executionContext: ExecutionContext) {

  /**
    * Creates a GraphQLContext instance and implicitly injects an object of a HTTP request with a graphql query, that's
    * going to be executed inside this context into it.
    *
    * @return a GraphQLContext, that contains all repositories and services that are needed for resolvers.
    */
  def createContextForRequest() = GraphQLContext(
    itemResolver,
    executionContext
  )
}

/**
  * A GraphQLContext, which can contain all the repositories, services and resolvers of you need.
  *
  * @param itemResolver a resolver class that contains all resolver methods for the Item model.
  */
case class GraphQLContext(itemResolver: ItemResolver,
                          executionContext: ExecutionContext)
