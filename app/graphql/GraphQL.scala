package graphql

import models.Item
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
        Item.GraphQL.Queries ++
      )
    ),

    mutation = Some(
      ObjectType("Mutation",
        fields(
          Item.GraphQL.Mutations ++
        )
      )
    )
  )
}