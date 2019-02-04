package models

import slick.jdbc.H2Profile.api.{Table => SlickTable, _}
import slick.lifted.{Tag => SlickTag}
import spray.json.{DefaultJsonProtocol, JsonFormat}

/**
  * The main entity over which we will carry out the CRUD operations.
  *
  * @param id      id of the entity
  * @param title   post's title
  * @param content post's content
  */
case class Post(id: Option[Long] = None, title: String, content: String)

/**
  * Defined slick table for entity 'Post'
  */
object Post extends ((Option[Long], String, String) => Post) {

  class Table(slickTag: SlickTag) extends SlickTable[Post](slickTag, "POSTS") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def title = column[String]("TITLE")

    def content = column[String]("CONTENT")

    def * = (id.?, title, content).mapTo[Post]
  }

}

/**
  * Converts Post in JSON format to Post object
  */
object PostJsonProtocol extends DefaultJsonProtocol {
  implicit val postJsonProtocolFormat: JsonFormat[Post] = jsonFormat3(Post)
}


