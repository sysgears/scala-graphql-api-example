package models

import graphql.GraphQLContext
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}
import sangria.schema._

/**
  * The object contains the definitions of all query and mutations
  * that work with the entity 'Item'. Also it is a construction element
  * for the build graphql schema of the entire application.
  */
object ItemSchema {

  /**
    * Used to convert an Item object to a Sangria graphql object.
    * Sangria macros deriveObjectType creates an ObjectType with fields found in the Item entity.
    */
  implicit val Item: ObjectType[Unit, Item] = deriveObjectType[Unit, Item](ObjectTypeName("Item"))

  /**
    * Queries to work with the entity of Item.
    */
  val Queries: List[Field[GraphQLContext, Unit]] = List(
    Field(
      name = "items",
      fieldType = ListType(Item),
      resolve = {
        sangriaContext =>
          sangriaContext.ctx.itemResolver.items
      }
    ),
    Field(
      name = "findItem",
      fieldType = OptionType(Item),
      arguments = List(
        Argument("id", LongType)
      ),
      resolve = {
        sangriaContext =>
          sangriaContext.ctx.itemResolver.findItem(sangriaContext.args.arg[Long]("id"))
      }
    )
  )

  /**
    * Mutations to work with the entity of Item.
    */
  val Mutations: List[Field[GraphQLContext, Unit]] = List(
    Field(
      name = "addItem",
      fieldType = Item,
      arguments = List(
        Argument("description", StringType)
      ),
      resolve = {
        sangriaContext =>
          sangriaContext.ctx.itemResolver.addItem(
            sangriaContext.args.arg[String]("description")
          )
      }
    )
  )
}
