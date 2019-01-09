package graphql

import com.google.inject.Inject
import models.ItemSchema
import sangria.schema.{ObjectType, fields}

/**
  * Base component for the GraphQL schema.
  *
  * @param itemSchema an object containing all queries and mutations to work with the entity of 'Item'
  */
class GraphQL @Inject()(itemSchema: ItemSchema) {

  /**
    * Contains a graphql schema of the entire application.
    * We can add queries, mutations, etc. for each model.
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