package models

import spray.json.{DefaultJsonProtocol, JsonFormat}

/**
  * The main entity over which we will carry out the CRUD operations.
  *
  * @param id      id of the entity
  * @param title   post's title
  * @param content post's content
  */
case class Post(id: Option[Long] = None, title: String, content: String)

object PostJsonProtocol extends DefaultJsonProtocol {
  implicit val postJsonProtocolFormat: JsonFormat[Post] = jsonFormat3(Post)
}


