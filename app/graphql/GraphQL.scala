package graphql

import com.google.inject.Inject
import models.ItemSchema
import sangria.schema.{ObjectType, fields}

/**
  * Base component for the GraphQL schema.
  */
class GraphQL @Inject()(itemSchema: ItemSchema) {

  /**
    * Enables to limit the maximum depth of incoming queries.
    */
  val maxQueryDepth = 15

  /**
    * Enables to limit the maximum complexity of incoming queries.
    */
  val maxQueryComplexity = 1000

  /**
    * Since the schema are shared among ALL models, here we can add queries, mutations, etc. for each model.
    */
  val Schema = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        itemSchema.Queries: _*
      )
    ),

    mutation = Some(
      ObjectType("Mutation",
        fields(
          itemSchema.Mutations: _*
        )
      )
    )
  )
}