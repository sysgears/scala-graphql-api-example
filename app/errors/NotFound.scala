package errors

import sangria.execution.UserFacingError

/**
  * Represents an exception object indicating that something was not found.
  *
  * @param msg an exception message to show
  */
case class NotFound(msg: String) extends Exception(msg) with UserFacingError {
  override def getMessage(): String = msg
}

