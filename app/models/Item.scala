package models

import graphql.GraphQLContext
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}
import sangria.marshalling.{CoercedScalaResultMarshaller, FromInput, ResultMarshaller}
import sangria.schema.{Argument, Field, InputField, InputObjectType, ObjectType}

case class Item(id: Long, description: String)

object Item {

  object GraphQL {

    case class ItemInput(description: String)

    case class ItemsPayload(items: List[Item])

    implicit val itemInputUnmarshaller: FromInput[ItemInput] = inputUnmarshaller {
      input =>
        ItemInput(
          description = input("description").asInstanceOf[String]
        )
    }

    object Types {

      implicit val Item: ObjectType[GraphQLContext, Item] = deriveObjectType[GraphQLContext, Item](ObjectTypeName("Item"))
      implicit val ItemsPayload: ObjectType[Unit, ItemsPayload] = deriveObjectType(ObjectTypeName("ItemsPayload"))

      val ItemInput: InputObjectType[ItemInput] = InputObjectType[ItemInput](
        name = "ItemInput",
        fields = List(
          InputField(name = "description", fieldType = sangria.schema.StringType)
        )
      )
    }

    val Queries: List[Field[GraphQLContext, Unit]] = List(
      Field(
        name = "items",
        fieldType = Types.ItemsPayload,
        resolve = {
          sangriaContext =>
            sangriaContext.ctx.itemResolver.items
        }
      )
    )

    val Mutations: List[Field[GraphQLContext, Unit]] = List(
      Field(
        name = "addItem",
        fieldType = Types.Item,
        arguments = List(
          Argument("input", Types.ItemInput)
        ),
        resolve = {
          sangriaContext =>
            sangriaContext.ctx.itemResolver.addItem(
              sangriaContext.args.arg[ItemInput]("input")
            )
        }
      )
    )
  }

  def inputUnmarshaller[T](inputToClassFunc: (Map[String, Any] => T)) = new FromInput[T] {
    val marshaller: ResultMarshaller = CoercedScalaResultMarshaller.default

    override def fromResult(node: marshaller.Node): T = inputToClassFunc(node.asInstanceOf[Map[String, Any]])
  }
}
