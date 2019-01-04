package graphql

import models.ItemSchema
import sangria.schema.{ObjectType, Schema, fields}

/**
  * Base component for the GraphQL schema.
  */
object GraphQL {

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
  val Schema: Schema[GraphQLContext, Unit] = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        ItemSchema.Queries: _*
      )
    ),

    mutation = Some(
      ObjectType("Mutation",
        fields(
          ItemSchema.Mutations: _*
        )
      )
    )
  )
}