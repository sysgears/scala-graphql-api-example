package graphql.schemas

import com.google.inject.Inject
import graphql.resolvers.PostResolver
import models.Post
import sangria.macros.derive.{ObjectTypeName, deriveObjectType}
import sangria.schema._

/**
  * Contains the definitions of all query and mutations
  * that work with the entity 'Post'. Also it is a construction element
  * for the build graphql schema of the entire application.
  *
  * @param postResolver an object containing all resolve functions to work with the entity of 'Post'
  */
class PostSchema @Inject()(postResolver: PostResolver) {

  /**
    * Сonvert an Post object to a Sangria graphql object.
    * Sangria macros deriveObjectType creates an ObjectType with fields found in the Post entity.
    */
  implicit val PostType: ObjectType[Unit, Post] = deriveObjectType[Unit, Post](ObjectTypeName("Post"))


  /**
    * List of queries to work with the entity of Post
    */
  val Queries: List[Field[Unit, Unit]] = List(
    Field(
      name = "posts",
      fieldType = ListType(PostType),
      resolve = _ => postResolver.posts
    ),
    Field(
      name = "findPost",
      fieldType = OptionType(PostType),
      arguments = List(
        Argument("id", LongType)
      ),
      resolve =
        sangriaContext =>
          postResolver.findPost(sangriaContext.args.arg[Long]("id"))
    )
  )

  /**
    * List of mutations to work with the entity of Post.
    */
  val Mutations: List[Field[Unit, Unit]] = List(
    Field(
      name = "addPost",
      fieldType = PostType,
      arguments = List(
        Argument("title", StringType),
        Argument("content", StringType)
      ),
      resolve = sangriaContext =>
        postResolver.addPost(
          sangriaContext.args.arg[String]("title"),
          sangriaContext.args.arg[String]("content")
        )
    ),
    Field(
      name = "updatePost",
      fieldType = PostType,
      arguments = List(
        Argument("id", LongType),
        Argument("title", StringType),
        Argument("content", StringType)
      ),
      resolve = sangriaContext =>
        postResolver.updatePost(
          sangriaContext.args.arg[Long]("id"),
          sangriaContext.args.arg[String]("title"),
          sangriaContext.args.arg[String]("content")
        )
    ),
    Field(
      name = "deletePost",
      fieldType = BooleanType,
      arguments = List(
        Argument("id", LongType)
      ),
      resolve =
        sangriaContext =>
          postResolver.deletePost(sangriaContext.args.arg[Long]("id"))
    )
  )
}
