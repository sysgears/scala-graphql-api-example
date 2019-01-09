package models

import com.google.inject.Inject
import resolvers.ItemResolver
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}
import sangria.schema._

/**
  * Contains the definitions of all query and mutations
  * that work with the entity 'Item'. Also it is a construction element
  * for the build graphql schema of the entire application.
  *
  * @param itemResolver an object containing all resolve functions to work with the entity of 'Item'
  */
class ItemSchema @Inject()(itemResolver: ItemResolver) {

  /**
    * Сonvert an Item object to a Sangria graphql object.
    * Sangria macros deriveObjectType creates an ObjectType with fields found in the Item entity.
    */
  implicit val Item: ObjectType[Unit, Item] = deriveObjectType[Unit, Item](ObjectTypeName("Item"))

  /**
    * List of queries to work with the entity of Item
    */
  val Queries: List[Field[Unit, Unit]] = List(
    Field(
      name = "items",
      fieldType = ListType(Item),
      resolve = _ => itemResolver.items
    ),
    Field(
      name = "findItem",
      fieldType = OptionType(Item),
      arguments = List(
        Argument("id", LongType)
      ),
      resolve =
        sangriaContext =>
          itemResolver.findItem(sangriaContext.args.arg[Long]("id"))
    )
  )

  /**
    * List of mutations to work with the entity of Item.
    */
  val Mutations: List[Field[Unit, Unit]] = List(
    Field(
      name = "addItem",
      fieldType = Item,
      arguments = List(
        Argument("description", StringType)
      ),
      resolve = sangriaContext =>
        itemResolver.addItem(
          sangriaContext.args.arg[String]("description")
        )
    )
  )
}
