package errors

import sangria.execution.UserFacingError

/**
  * Represents an exception object indicating that something is already exists.
  *
  * @param msg an exception message to show
  */
case class AlreadyExists(msg: String) extends Exception with UserFacingError {
  override def getMessage: String = msg
}
