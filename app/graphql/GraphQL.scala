package graphql

import models.ItemSchema
import sangria.schema.{ObjectType, Schema, fields}

/**
  * Base component for the GraphQL schema.
  */
object GraphQL {

  val maxQueryDepth = 15
  val maxQueryComplexity = 1000

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