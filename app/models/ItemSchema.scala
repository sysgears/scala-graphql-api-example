package models

import graphql.GraphQLContext
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}
import sangria.schema._

object ItemSchema {

  implicit val Item: ObjectType[Unit, Item] = deriveObjectType[Unit, Item](ObjectTypeName("Item"))

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
