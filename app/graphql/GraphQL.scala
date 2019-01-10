package graphql

import com.google.inject.Inject
import graphql.schemas.PostSchema
import sangria.schema.{ObjectType, fields}

/**
  * Base component for the GraphQL schema.
  *
  * @param postSchema an object containing all queries and mutations to work with the entity of 'Post'
  */
class GraphQL @Inject()(postSchema: PostSchema) {

  /**
    * Contains a graphql schema of the entire application.
    * We can add queries, mutations, etc. for each model.
    */
  val Schema = sangria.schema.Schema(
    query = ObjectType("Query",
      fields(
        postSchema.Queries: _*
      )
    ),

    mutation = Some(
      ObjectType("Mutation",
        fields(
          postSchema.Mutations: _*
        )
      )
    )
  )
}